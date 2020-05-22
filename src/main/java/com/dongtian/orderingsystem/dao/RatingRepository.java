package com.dongtian.orderingsystem.dao;

import com.dongtian.orderingsystem.pojo.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Integer> {
    List<Rating> findRatingsByProductId(String productId);
}
