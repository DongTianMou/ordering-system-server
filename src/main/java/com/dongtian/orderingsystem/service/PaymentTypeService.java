package com.dongtian.orderingsystem.service;

import com.dongtian.orderingsystem.pojo.PaymentType;

import java.util.List;

public interface PaymentTypeService {
    PaymentType getByTypeId(Integer id);
    void save(PaymentType paymentType);

    List<PaymentType> findAll();
}
