package com.dongtian.orderingsystem.service.impl;

import com.dongtian.orderingsystem.dao.PaymentInfoRepository;
import com.dongtian.orderingsystem.dto.OrderDto;
import com.dongtian.orderingsystem.enums.PayStatusEnum;
import com.dongtian.orderingsystem.pojo.PaymentInfo;
import com.dongtian.orderingsystem.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {
    @Autowired
    private PaymentInfoRepository paymentInfoRepository;
    @Override
    public PaymentInfo create(OrderDto orderDto) {
        Integer price = (orderDto.getOrderAmount().multiply(new BigDecimal(100))).intValue();
        PaymentInfo paymentInfo = PaymentInfo.builder()
                .infoId(1)
                .orderId(orderDto.getOrderId())
                .price(price)
                .paymentTypeId(PayStatusEnum.TYPE.getCode())
                .createTime(orderDto.getCreateTime())
                .updateTime(orderDto.getUpdateTime())
                .build();
        paymentInfoRepository.save(paymentInfo);
        return paymentInfo;
    }

    @Override
    public PaymentInfo findByOrderId(String orderId) {
        return paymentInfoRepository.findByOrderId(orderId);
    }

    @Override
    public void save(PaymentInfo paymentInfo) {
        paymentInfoRepository.save(paymentInfo);
    }
}
