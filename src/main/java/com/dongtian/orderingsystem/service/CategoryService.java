package com.dongtian.orderingsystem.service;


import com.dongtian.orderingsystem.pojo.ProductCategory;

import java.util.List;

public interface CategoryService {

    ProductCategory getOne(Integer id);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
