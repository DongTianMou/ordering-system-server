package com.dongtian.orderingsystem.service;

import com.dongtian.orderingsystem.dto.OrderDto;
import com.dongtian.orderingsystem.pojo.PaymentInfo;

public interface PaymentInfoService {
    PaymentInfo create(OrderDto orderDto);

    PaymentInfo findByOrderId(String orderId);

    void save(PaymentInfo paymentInfo);
}
