package com.dongtian.orderingsystem.service.impl;

import com.dongtian.orderingsystem.dao.RatingRepository;
import com.dongtian.orderingsystem.pojo.Rating;
import com.dongtian.orderingsystem.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    private RatingRepository ratingRepository;
    @Override
    public List<Rating> findByProductId(String productId) {
        return ratingRepository.findRatingsByProductId(productId);
    }

    @Override
    public void save(Rating rating) {
        ratingRepository.save(rating);
    }

    @Override
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }
}
