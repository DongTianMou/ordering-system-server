package com.dongtian.orderingsystem.dao;

import com.dongtian.orderingsystem.pojo.OrderInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderInfoRepository extends JpaRepository<OrderInfo,String> {
    Page<OrderInfo> findByBuyerOpenid(String openid, Pageable pageable);

    List<OrderInfo> findAllByBuyerOpenid(String openid);
}
