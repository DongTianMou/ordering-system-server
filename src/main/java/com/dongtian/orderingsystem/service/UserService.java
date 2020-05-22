package com.dongtian.orderingsystem.service;

import com.dongtian.orderingsystem.dto.OrderDto;
import com.dongtian.orderingsystem.pojo.User;

//为了避免查询订单信息时，用户只提供orderId就可以查询消息，新增了接口userService，必须检验openId
public interface UserService {
    /** 买家查询订单*/
    OrderDto findOrderOne(String openid, String orderId);
    /** 买家取消订单*/
    OrderDto cancelOrder(String openid, String orderId);
//    /** 新增用户*/
////    void addUser(UserDto user);
////    /** 新增用户*/
////    User updateUser(UserDto user);
    User findByUsername(String username);
}
