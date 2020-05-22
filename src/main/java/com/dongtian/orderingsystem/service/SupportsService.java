package com.dongtian.orderingsystem.service;

import com.dongtian.orderingsystem.pojo.Support;

import java.util.List;

public interface SupportsService {

    Support save(Support support);

    List<Support> findAll();

    Support getByType(Integer type);

    Support getOne(Integer id);
}
