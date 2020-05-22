package com.dongtian.orderingsystem.dao;

import com.dongtian.orderingsystem.pojo.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PaymentTypeRepository extends JpaRepository<PaymentType,Integer> {
    PaymentType getByTypeId(Integer id);
    List<PaymentType> findAll();
}
