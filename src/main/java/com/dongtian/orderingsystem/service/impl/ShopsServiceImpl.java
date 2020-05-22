package com.dongtian.orderingsystem.service.impl;

import com.dongtian.orderingsystem.dao.ShopsRepository;
import com.dongtian.orderingsystem.dao.SupportsRepository;
import com.dongtian.orderingsystem.dto.ShopsDto;
import com.dongtian.orderingsystem.pojo.ShopsInfo;
import com.dongtian.orderingsystem.pojo.Support;
import com.dongtian.orderingsystem.service.ShopsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopsServiceImpl implements ShopsService {
    @Autowired
    private ShopsRepository shopsRepository;
    @Autowired
    private SupportsRepository supportsRepository;
    @Override
    public ShopsDto getOne() {
        ShopsDto shopsDto = new ShopsDto();
        ShopsInfo shopsInfo =  shopsRepository.getOne(1);
        List<Support> supports = supportsRepository.findAll();
        //把shopsInfo中属性复制到shopsDto中
        BeanUtils.copyProperties(shopsInfo, shopsDto);
        shopsDto.setSupports(supports);
        return shopsDto;
    }

    @Override
    public ShopsInfo getOne(Integer id) {
        return shopsRepository.getOne(id);
    }


    @Override
    public void save(ShopsInfo shopsInfo) {
        shopsRepository.save(shopsInfo);
    }

    @Override
    public List<ShopsInfo> findAll() {
        return shopsRepository.findAll();
    }
}
