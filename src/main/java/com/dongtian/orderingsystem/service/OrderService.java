package com.dongtian.orderingsystem.service;

import com.dongtian.orderingsystem.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    /** 创建订单. */
    OrderDto create(OrderDto orderDto);
    /** 查询单个订单. */
    OrderDto findOneByOrderID(String orderId);
    /** 取消订单. */
    OrderDto cancel(OrderDto orderDto);
    /** 订单完成. */
    OrderDto finish(OrderDto orderDto);
    /** 支付订单. */
    OrderDto paid(OrderDto orderDto);
    /** 后台查询订单列表. */
    Page<OrderDto> findList(Pageable pageable);
    /** 前台查询订单列表. */
    List<OrderDto> findAll(String openid);
}
