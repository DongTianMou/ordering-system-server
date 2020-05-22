package com.dongtian.orderingsystem.service.impl;

import com.dongtian.orderingsystem.dao.PaymentTypeRepository;
import com.dongtian.orderingsystem.pojo.PaymentType;
import com.dongtian.orderingsystem.service.PaymentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentTypeServiceImpl implements PaymentTypeService {
    @Autowired
    private PaymentTypeRepository paymentTypeDao;
    @Override
    public PaymentType getByTypeId(Integer id) {
        PaymentType paymentType = paymentTypeDao.getByTypeId( id );
        if (paymentType == null) {
            throw new RuntimeException("查找paymentType == null");
        }
        return paymentType;
    }

    @Override
    public void save(PaymentType paymentType) {
        if (paymentType == null){
            throw new RuntimeException("paymentType 为空");
        }
        paymentTypeDao.save(paymentType);
    }

    @Override
    public List<PaymentType> findAll() {
       List<PaymentType> paymentTypes = paymentTypeDao.findAll();
       return paymentTypes;
    }
}
