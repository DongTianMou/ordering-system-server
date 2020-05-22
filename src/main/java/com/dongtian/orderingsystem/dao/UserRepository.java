package com.dongtian.orderingsystem.dao;

import com.dongtian.orderingsystem.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Integer countByTelephoneAndIdIsNotLike(String telephone, Integer id);

    User findByUsername(String username);
}
