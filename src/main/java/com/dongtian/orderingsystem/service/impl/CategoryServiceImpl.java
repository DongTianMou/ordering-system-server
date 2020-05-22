package com.dongtian.orderingsystem.service.impl;

import com.dongtian.orderingsystem.dao.ProductCategoryRepository;
import com.dongtian.orderingsystem.pojo.ProductCategory;
import com.dongtian.orderingsystem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ProductCategoryRepository categoryRepository;
    //查一个类目
    @Override
    public ProductCategory getOne(Integer id) {
        return categoryRepository.getOne( id );
    }
    //查寻所有一个类目
    @Override
    public List<ProductCategory> findAll() {
        return categoryRepository.findAll();
    }
    //查寻指定类型中的类目
    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return categoryRepository.findByCategoryTypeIn(categoryTypeList);
    }

    //保存类目
    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return categoryRepository.save( productCategory );
    }
}
