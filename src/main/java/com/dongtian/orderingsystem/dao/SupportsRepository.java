package com.dongtian.orderingsystem.dao;

import com.dongtian.orderingsystem.pojo.Support;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportsRepository extends JpaRepository<Support,Integer> {
    Support getByType(Integer type);
}
