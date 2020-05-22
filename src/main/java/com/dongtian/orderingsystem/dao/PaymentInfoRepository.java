package com.dongtian.orderingsystem.dao;


import com.dongtian.orderingsystem.pojo.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfo,Integer> {
    PaymentInfo findByOrderId(String orderId);
}
