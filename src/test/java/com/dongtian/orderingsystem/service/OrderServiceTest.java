package com.dongtian.orderingsystem.service;

import com.dongtian.orderingsystem.dto.OrderDto;
import com.dongtian.orderingsystem.enums.OrderStatusEnum;
import com.dongtian.orderingsystem.enums.PayStatusEnum;
import com.dongtian.orderingsystem.pojo.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    private final String BUYER_OPENID = "12345678";
    private final String ORDER_ID = "1584571036163337896";
    @Test
    void create() {
        OrderDto orderDTO = new OrderDto();
        orderDTO.setBuyerName("林东填");
        orderDTO.setBuyerAddress("广东工业大学");
        orderDTO.setBuyerPhone("13719300942");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("1");
        o1.setProductQuantity(1);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("2");
        o2.setProductQuantity(2);

        orderDetailList.add(o1);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDto result = orderService.create(orderDTO);
        log.info("【创建订单】result={}", result);
        Assert.assertNotNull(result);
    }

    @Test
    void findOneByOrderID() {
        orderService.findOneByOrderID(ORDER_ID);
    }

    @Test
    void findList() {
        PageRequest request = PageRequest.of( 0,2);
        Page<OrderDto> orderDtoPage = orderService.findList(request);
        System.out.println(orderDtoPage.getContent());
        Assert.assertTrue("后台查询订单列表信息",orderDtoPage.getTotalElements()>0);
    }

    @Test
    void cancel() {
        OrderDto orderDto = orderService.findOneByOrderID(ORDER_ID);
        OrderDto result = orderService.cancel(orderDto);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());
    }

    @Test
    void finish() {
        OrderDto orderDto = orderService.findOneByOrderID(ORDER_ID);
        OrderDto result = orderService.finish(orderDto);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), result.getOrderStatus());
    }

    @Test
    void paid() {
        OrderDto orderDto = orderService.findOneByOrderID(ORDER_ID);
        OrderDto result = orderService.paid(orderDto);
        System.out.println(result.getPayStatus());
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getPayStatus());
    }
}