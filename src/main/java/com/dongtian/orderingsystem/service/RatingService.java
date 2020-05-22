package com.dongtian.orderingsystem.service;

import com.dongtian.orderingsystem.pojo.Rating;

import java.util.List;

public interface RatingService {

    List<Rating> findByProductId(String productId);

    void save(Rating rating);

    List<Rating> findAll();
}
