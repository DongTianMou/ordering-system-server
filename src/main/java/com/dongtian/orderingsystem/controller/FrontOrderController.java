package com.dongtian.orderingsystem.controller;

import com.dongtian.orderingsystem.convert.OrderFormToOrderDto;
import com.dongtian.orderingsystem.dto.OrderDto;
import com.dongtian.orderingsystem.enums.ResultEnum;
import com.dongtian.orderingsystem.exceptions.ParamException;
import com.dongtian.orderingsystem.form.OrderForm;
import com.dongtian.orderingsystem.service.OrderService;
import com.dongtian.orderingsystem.service.UserService;
import com.dongtian.orderingsystem.utils.ResultVoUtils;
import com.dongtian.orderingsystem.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/front/order")
public class FrontOrderController extends BaseApiController{
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    /** 创建订单: 卖家订餐，从前端传过来的信息主要有卖家信息，用一个表单进行接受，
     *           并使用校订规则进行检验
     * */
    @PostMapping("/create")
    public ResultVo<Map<String,String>> create(@Valid OrderForm orderForm,
                                               BindingResult bindingResult){
        //判断检验结果是否正确
        if (bindingResult.hasErrors()){
            log.error( "[参数校验失败]，orderForm={}",orderForm );
            throw new ParamException( ResultEnum.ORDER_PARAM_CHECK_ERROR);
        }
        //进行参数转换，把orderFrom --> orderDto
        OrderDto orderDto = OrderFormToOrderDto.convert(orderForm);
        //判断是否为空
        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())) {
            log.error("[创建订单],提交的购物车不能为空");
            throw new ParamException(ResultEnum.ORDER_DETAIL_IS_NULL);
        }
        //生成订单
        OrderDto orderDtoInfo = orderService.create(orderDto);
        //拿到结果适应返回
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderDtoInfo.getOrderId());
        return ResultVoUtils.success( map );
    }
    /** 查询订单列表.
     * 通过用户的微信id,默认从第0页开始查，每页显示5组订单信息*/
    @GetMapping("/list")
    public ResultVo<List<OrderDto>> orderList(
            @RequestParam( value = "openid") String openid){
        //判断openid是否为空
        if (StringUtils.isEmpty( openid )){
            log.error( "[查询订单列表]--openid为空" );
            throw new ParamException( ResultEnum.OPENID_CHECK_ERROR);
        }

        //调用事务层的findList()
        List<OrderDto> orderDtoList = orderService.findAll( openid);

        return ResultVoUtils.success(orderDtoList);
    }

    /** 查询单个订单详情*/
    @GetMapping("/detail")
    public ResultVo<OrderDto> orderDetail(
            @RequestParam( value ="openid") String openid,
            @RequestParam( value ="orderId") String orderId){
        //判断openid是否为空
        if (StringUtils.isEmpty( openid )){
            log.error( "[查询订单列表]--openid为空" );
            throw new ParamException( ResultEnum.OPENID_CHECK_ERROR);
        }
        //判断orderI是否为空
        if (StringUtils.isEmpty( openid )){
            log.error( "[查询订单列表]--orderId为空" );
            throw new ParamException( ResultEnum.ORDER_NOT_EXIST);
        }
        OrderDto orderDto = userService.findOrderOne(openid,orderId);
        return ResultVoUtils.success(orderDto);
    }
    /** 取消订单. */
    @PostMapping("/cancel")
    public ResultVo cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId) {
        userService.cancelOrder( openid,orderId );
        return ResultVoUtils.success();
    }
}
