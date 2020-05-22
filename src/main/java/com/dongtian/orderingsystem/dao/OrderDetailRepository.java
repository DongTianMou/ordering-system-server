package com.dongtian.orderingsystem.dao;

import com.dongtian.orderingsystem.pojo.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
    List<OrderDetail> findOrderDetailByOrderIdIs(String orderId);
}
