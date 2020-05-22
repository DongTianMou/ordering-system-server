package com.dongtian.orderingsystem.service.impl;

import com.dongtian.orderingsystem.dao.SupportsRepository;
import com.dongtian.orderingsystem.pojo.Support;
import com.dongtian.orderingsystem.service.SupportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupportsServiceImpl implements SupportsService {
    @Autowired
    private SupportsRepository supportsRepository;
    @Override
    public Support save(Support support) {
        return supportsRepository.save(support);
    }

    @Override
    public List<Support> findAll() {
        return supportsRepository.findAll();
    }

    @Override
    public Support getByType(Integer type) {
        return supportsRepository.getByType(type);
    }

    @Override
    public Support getOne(Integer id) {
        return supportsRepository.getOne(id);
    }

}
