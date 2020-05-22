package com.dongtian.orderingsystem.service.impl;

import com.dongtian.orderingsystem.dao.UserRepository;
import com.dongtian.orderingsystem.dto.OrderDto;
import com.dongtian.orderingsystem.enums.ResultEnum;
import com.dongtian.orderingsystem.exceptions.ParamException;
import com.dongtian.orderingsystem.pojo.User;
import com.dongtian.orderingsystem.service.OrderService;
import com.dongtian.orderingsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository repository;

    private final static String PASSWORD = "123456";
    @Override
    public OrderDto findOrderOne(String openid, String orderId) {
        //根据orderId判断是不是有订单
        //根据openid判断是否是自己的订单
        return CheckOrderAndSelf( openid,orderId );
    }

    @Override
    public OrderDto cancelOrder(String openid, String orderId) {
        //根据orderId判断是不是有订单
        //根据openid判断是否是自己的订单
        OrderDto orderDto = CheckOrderAndSelf( openid,orderId );
        if (orderDto == null) {
            log.error("查不到改订单, orderId={}", orderId);
            throw new ParamException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel( orderDto );
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }

//    @Override
//    public void addUser(UserDto param) {
//        BeanValidator.check(param);
//        if(checkTelephoneExist(param.getTelephone(), param.getId())) {
//            throw new ParamException(ResultEnum.TELEPHONE_HAS_BE_USED);
//        }
//        if(checkEmailExist(param.getMail(), param.getId())) {
//            throw new ParamException(ResultEnum.MAIL_HAS_BE_USED);
//        }
//        String encryptedPassword = MD5Util.encrypt(PASSWORD);
//        User user = User.builder()
//                .username( param.getUsername() )
//                .telephone( param.getTelephone() )
//                .password( encryptedPassword )
//                ).build();

//        // TODO: sendEmail
//        //保存用户到数据库
//        repository.save(user);
//        //TODO 日志记录
//        //LogService.saveUserLog(null, user);
//    }
//
//    @Override
//    public User updateUser(UserDto user) {
//        return null;
//    }


    private OrderDto CheckOrderAndSelf(String openid, String orderId){
        //根据orderId判断是不是有订单
        OrderDto orderDto = orderService.findOneByOrderID( orderId );
        if (orderDto==null){
            return null;
        }
        //根据openid判断是否是自己的订单
        if (!orderDto.getBuyerOpenid(  ).equals( openid )){
            log.error("openId不一致");
            throw new ParamException( ResultEnum.OPENID_CHECK_ERROR);
        }
        return orderDto;
    }

    //除了这个id之外的用户是否有相同的电话或者邮箱，检验邮箱和电话
    private boolean checkTelephoneExist(String telephone, Integer userId) {
        return repository.countByTelephoneAndIdIsNotLike(telephone, userId) > 0;
    }
}
