package com.dongtian.orderingsystem.dao;

import com.dongtian.orderingsystem.pojo.ShopsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopsRepository extends JpaRepository<ShopsInfo,Integer> {
}
