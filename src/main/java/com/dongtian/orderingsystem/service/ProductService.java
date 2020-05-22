package com.dongtian.orderingsystem.service;

import com.dongtian.orderingsystem.dto.CartDto;
import com.dongtian.orderingsystem.pojo.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    //根据id查询特定商品
    ProductInfo findOne(String productId);

    // 查询所有在架商品列表
    List<ProductInfo> findUpAll();

    // 后台分页查询指定页的在架商品列表
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDto> cartDtoList);

    //减库存
    void decreaseStock(List<CartDto> cartDtoList);

    //正常在售
    ProductInfo onSale(String productId);

    //下架
    ProductInfo offSale(String productId);
}
