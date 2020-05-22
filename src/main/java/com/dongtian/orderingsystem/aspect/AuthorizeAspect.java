package com.dongtian.orderingsystem.aspect;

import com.dongtian.orderingsystem.constant.CookieConstant;
import com.dongtian.orderingsystem.constant.RedisConstant;
import com.dongtian.orderingsystem.exceptions.AuthorizeException;
import com.dongtian.orderingsystem.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class AuthorizeAspect {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.dongtian.orderingsystem.controller.Manage*.*(..))" +
            "&& !execution(public * com.dongtian.orderingsystem.controller.UserController.*(..))")
    public void verify() {}

    @Before("verify()")
    public ModelAndView doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //查询请求中有没有name为token的cookie --key为token --value为UUID的值
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null) {
            log.warn("【登录校验】Cookie中查不到token");
            throw new AuthorizeException();
        }

        //去redis里查询userName --key 格式化UUID --value为userName
        String tokenValue = redisTemplate.opsForValue().get(
                String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)) {
            log.warn("【登录校验】Redis中查不到token");
            throw new AuthorizeException();
        }
        return null;
    }


}
