package com.dongtian.orderingsystem.common;

import com.dongtian.orderingsystem.pojo.User;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
@Data
public class RequestHolder {
    //用户登陆后把用户信息放进ThreadLocal,需要的时候拿来用就好
    //map-->key:当前进程
    private static final ThreadLocal<User> userHolder = new ThreadLocal<>();
    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();

    //放入user
    public static void add(User user) {
        userHolder.set(user);
    }

    public static void add(HttpServletRequest request) {
        requestHolder.set(request);
    }

    public static User getCurrentUser() {
       return userHolder.get();
    }

    public static HttpServletRequest getCurrentRequest() {
        return requestHolder.get();
    }
    //移除：不然会一直占用内存
    public static void remove() {
        userHolder.remove();
        requestHolder.remove();
    }
}
