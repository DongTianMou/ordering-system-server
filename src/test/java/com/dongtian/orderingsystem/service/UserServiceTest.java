package com.dongtian.orderingsystem.service;

import com.dongtian.orderingsystem.utils.MD5Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserServiceTest {
    @Autowired
    private UserService userService;
    @Test
    void addUser() {
        String encryptedPassword = MD5Util.encrypt("123456");
        System.out.println(encryptedPassword);
    }
}