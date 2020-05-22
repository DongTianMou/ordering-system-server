package com.dongtian.orderingsystem.service;

import com.dongtian.orderingsystem.dto.ShopsDto;
import com.dongtian.orderingsystem.pojo.ShopsInfo;

import java.util.List;

public interface ShopsService {
    ShopsDto getOne();

    ShopsInfo getOne(Integer id);

    void save(ShopsInfo shopsInfo);

    List<ShopsInfo> findAll();
}
