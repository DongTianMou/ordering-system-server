package com.dongtian.orderingsystem.service.impl;

import com.dongtian.orderingsystem.convert.OrderInfoToOrderDto;
import com.dongtian.orderingsystem.dao.OrderDetailRepository;
import com.dongtian.orderingsystem.dao.OrderInfoRepository;
import com.dongtian.orderingsystem.dto.CartDto;
import com.dongtian.orderingsystem.dto.OrderDto;
import com.dongtian.orderingsystem.enums.OrderStatusEnum;
import com.dongtian.orderingsystem.enums.PayStatusEnum;
import com.dongtian.orderingsystem.enums.ResultEnum;
import com.dongtian.orderingsystem.exceptions.ParamException;
import com.dongtian.orderingsystem.pojo.OrderDetail;
import com.dongtian.orderingsystem.pojo.OrderInfo;
import com.dongtian.orderingsystem.pojo.ProductInfo;
import com.dongtian.orderingsystem.service.OrderService;
import com.dongtian.orderingsystem.service.ProductService;
import com.dongtian.orderingsystem.utils.IdUtils;
import com.dongtian.orderingsystem.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

;


@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Override
    @Transactional
    public OrderDto create(OrderDto orderDto) {
        /*
        1.为订单生成唯一id，商品总价默认为0
        2、查询商品（数量, 价格）:根据orderDto查询订单详情，遍历订单详情中的productId
        3、计算订单总价:这里的商品单价不应该由客户传，需要我们自己去数据库查
        4.订单详情入库
        5.扣库存
         */
        // 1.为订单生成唯一id，商品总价默认为0,采用雪花算法
        IdWorker worker = new IdWorker(1,1,1);
        String orderId = Long.toString(worker.nextId());
        BigDecimal orderAmount = new BigDecimal( BigInteger.ZERO);
        //2.查询商品（数量, 价格）:根据orderDto查询订单详情，遍历订单详情中的productId
        List<OrderDetail> orderDetailList = orderDto.getOrderDetailList();
        for (OrderDetail orderDetail: orderDetailList) {
            ProductInfo productInfo = productService.findOne( orderDetail.getProductId());
            //商品不存在,抛异常
            if (productInfo == null) {
                throw new ParamException( ResultEnum.PRODUCT_NOT_EXIST);
            }
            //3.计算订单总价:这里的商品单价不应该由客户传，需要我们自己去数据库查
            orderAmount = productInfo.getProductPrice()
                    .multiply( new BigDecimal(orderDetail.getProductQuantity()))
                    .add( orderAmount );
            //4. 订单详情入库：设置唯一订单详情号
            orderDetail.setDetailId(  Long.toString(worker.nextId()) );
            orderDetail.setOrderId( orderId );
            //把productInfo中属性复制到orderDetail中
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setCreateTime(new Date());
            orderDetail.setUpdateTime( new Date());
            orderDetailRepository.save(orderDetail);
        }
        //4. 写入订单数据库（orderMaster)
        OrderInfo orderInfo = new OrderInfo();
        //将orderDto中的属性复制到orderMaster（多到少），相同属性会覆盖
        orderDto.setOrderId( orderId );
        BeanUtils.copyProperties(orderDto, orderInfo);
        //属性需要再设置
        orderInfo.setOrderAmount( orderAmount );
        orderInfo.setOrderStatus( OrderStatusEnum.NEW.getCode());
        orderInfo.setPayStatus( PayStatusEnum.WAIT.getCode());
        orderInfo.setCreateTime( new Date() );
        orderInfo.setUpdateTime( new Date() );
        orderInfoRepository.save(orderInfo);

       // 5. 扣库存（加事务）:收集购物车对象，遍历购物车中的商品id 和 购买数量
        List<CartDto> cartDtoList = orderDto.getOrderDetailList().stream()
                .map( e->new CartDto(e.getProductId(),e.getProductQuantity()))
                .collect( Collectors.toList() );
        productService.decreaseStock( cartDtoList );

        return orderDto;
    }

    @Override
    public OrderDto findOneByOrderID(String orderId) {
        //通过orderId查找
        OrderInfo orderInfo = orderInfoRepository.getOne(orderId);
        if (orderInfo == null){
            log.error("订单不存在");
            throw new ParamException( ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetails = orderDetailRepository.findOrderDetailByOrderIdIs(orderId);
        if (CollectionUtils.isEmpty(orderDetails)){
            log.error("订单为空");
            throw new ParamException( ResultEnum.ORDER_DETAIL_IS_NULL);
        }
        //组装dto
        OrderDto orderDto = OrderInfoToOrderDto.convert( orderInfo);
        //把orderDetailList设置仅orderDto
        orderDto.setOrderDetailList(orderDetails);
        return orderDto;
    }


    @Override
    @Transactional
    public OrderDto cancel(OrderDto orderDto) {
        //判断订单状态：是否为未完成订单
        if (!orderDto.getOrderStatus().equals( OrderStatusEnum.NEW.getCode())){
            log.info( "[取消订单]--订单状态错误，不是新订单,orderId={}, orderStatus={}", orderDto.getOrderId(), orderDto.getOrderStatus());
            throw new ParamException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态为取消状态
        OrderInfo orderInfo = new OrderInfo();
        orderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDto, orderInfo);
        orderInfo.setOrderStatus( OrderStatusEnum.CANCEL.getCode());
        orderInfo.setUpdateTime( new Date() );
        OrderInfo updateOrder = orderInfoRepository.save(orderInfo);
        if (updateOrder==null){
            log.info( "[取消订单]--订单状态更新错误, orderMaster={}", orderInfo);
            throw new ParamException(ResultEnum.ORDER_STATUS_UPDATE_ERROR);
        }
        //增加库存
        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())) {
            log.error("[取消订单]--订单中无商品详情, orderDto={}", orderDto);
            throw new  ParamException(ResultEnum.ORDER_DETAIL_IS_NULL);
        }
        List<CartDto> cartDtoList = orderDto.getOrderDetailList().stream()
                .map( e-> new CartDto( e.getProductId(),e.getProductQuantity() ) )
                .collect( Collectors.toList());
        productService.increaseStock( cartDtoList );
        //如果已经支付，退款
        //TODO
        return orderDto;

    }

    @Override
    @Transactional
    public OrderDto finish(OrderDto orderDto) {
        //判断订单状态：是否为未完成订单
        if (!orderDto.getOrderStatus().equals( OrderStatusEnum.NEW.getCode())){
            log.info( "[取消订单]--订单状态错误，不是新订单,orderId={}, orderStatus={}", orderDto.getOrderId(), orderDto.getOrderStatus());
            throw new ParamException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断订单支付状态
        if (orderDto.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("[完结订单]--订单未支付 orderPayStatus={}", orderDto.getPayStatus());
            throw new ParamException(ResultEnum.ORDER_IS_NOT_PAY);
        }
        //修改订单状态为取消状态
        orderDto.setOrderStatus( OrderStatusEnum.FINISHED.getCode() );

        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties( orderDto,orderInfo );
        orderInfo.setUpdateTime(new Date());
        OrderInfo updateOrder = orderInfoRepository.save(orderInfo);
        if (updateOrder==null){
            log.info( "[完结订单]--订单状态更新错误, orderStatus={}", orderInfo.getOrderStatus());
            throw new ParamException(ResultEnum.ORDER_STATUS_UPDATE_ERROR);
        }
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto paid(OrderDto orderDto) {
        /*
        * 1.查看订单状态
        * 2.修改状态
        * 3.更新数据库
        * */
        //判断订单状态
        if(!orderDto.getOrderStatus().equals( OrderStatusEnum.NEW.getCode() )){
            log.error( "[支付订单]--订单状态不正确，orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus() );
            throw new ParamException( ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断订单支付状态
        if (!orderDto.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("[支付订单]--订单已支付, orderDto={}", orderDto);
            throw new ParamException(ResultEnum.ORDER_HAS_PAY);
        }
        orderDto.setPayStatus( PayStatusEnum.SUCCESS.getCode() );
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties( orderDto,orderInfo );
        orderInfo.setUpdateTime(new Date());

        OrderInfo updateOrder = orderInfoRepository.save(orderInfo);
        if (updateOrder==null){
            log.info( "[支付订单]--订单支付状态更新错误, orderStatus={}", orderInfo.getOrderStatus());
            throw new ParamException(ResultEnum.ORDER_STATUS_UPDATE_ERROR);
        }
        return orderDto;
    }

    @Override
    public Page<OrderDto> findList(Pageable pageable) {
        Page<OrderInfo> orderInfoPage = orderInfoRepository.findAll(pageable);
        List<OrderDto> orderDtoList = OrderInfoToOrderDto.convert(orderInfoPage.getContent());
        return new PageImpl<OrderDto>(orderDtoList,pageable,orderInfoPage.getTotalElements());
    }

    @Override
    public List<OrderDto> findAll(String openid) {
        List<OrderInfo> orderDtos = orderInfoRepository.findAllByBuyerOpenid(openid);
        List<OrderDto> orderDtoList = OrderInfoToOrderDto.convert(orderDtos);
        return orderDtoList;
    }
}
