package com.dongtian.orderingsystem.dao;

import com.dongtian.orderingsystem.pojo.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
    /** 查看商品状态, 0正常1下架. */
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
