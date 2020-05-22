package com.dongtian.orderingsystem.controller;

import com.dongtian.orderingsystem.config.ProjectUrlConfig;
import com.dongtian.orderingsystem.constant.CookieConstant;
import com.dongtian.orderingsystem.constant.RedisConstant;
import com.dongtian.orderingsystem.form.UserForm;
import com.dongtian.orderingsystem.pojo.User;
import com.dongtian.orderingsystem.service.UserService;
import com.dongtian.orderingsystem.utils.CookieUtil;
import com.dongtian.orderingsystem.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    @GetMapping("/index")
    public ModelAndView loginPage(){
        return new ModelAndView("/user/loginPage");
    }
    @PostMapping("/login")
    public ModelAndView login(@Valid UserForm form, BindingResult bindingResult,
                              HttpServletResponse response,
                              Map<String,Object> map){
        //参数检查
        if (bindingResult.hasErrors()){
            map.put("msg", "用户名或密码错误");
            map.put("url", "/ordering-system/user/login");
            return new ModelAndView("common/error");
        }
        User user = userService.findByUsername(form.getUsername());
        if ( user == null ||
                !user.getPassword().equals(MD5Util.encrypt(form.getPassword()))){
            map.put("msg", "用户名或密码错误");
            map.put("url", "/ordering-system/user/login");
            return new ModelAndView("common/error");
        }
        //2. 设置token至redis
        String token = UUID.randomUUID().toString();
        // 设置过期时间为7200秒
        Integer expire = RedisConstant.EXPIRE;
        // K V Expire --key 格式化UUID --value为userName
        redisTemplate.opsForValue().set(
                String.format(RedisConstant.TOKEN_PREFIX, token),
                form.getUsername(),
                expire,
                TimeUnit.SECONDS);

        //3. 设置token至cookie K V Expire --name为token --value为UUID的值
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);

        return new ModelAndView("redirect:/manage/order/list");
    }
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String, Object> map) {
        //1. 从cookie里查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            //2. 清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));

            //3. 清除cookie
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }

        return new ModelAndView("/user/loginPage");
    }
}
