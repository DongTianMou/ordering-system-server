package com.dongtian.orderingsystem.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PRODUCT_NOT_EXIST(40001,"商品不存在"),
    ORDER_NOT_EXIST(40002,"订单不存在"),
    ORDER_DETAIL_IS_NULL(40003,"购物车/订单详情不存在"),
    USERNAME_NOT_EXIST(40004,"用户名不存在"),
    USER_NOT_EXIST(40005,"用户不存在"),
    TOKEN_NOT_EXIST(40006,"token为空,请于管理员联系"),
    ORDER_STATUS_ERROR(50001,"订单状态为已完成，无法取消"),
    ORDER_STATUS_UPDATE_ERROR(50002,"订单状态更新失败"),
    ORDER_IS_NOT_PAY(50003,"订单未支付"),
    ORDER_HAS_PAY(50004,"订单已支付"),
    ORDER_PARAM_CHECK_ERROR(50006,"订单参数校验失败"),
    PRODUCT_STOCK_NOTENOUGH(50005,"库存不足"),
    OBJECT_CONVERSION_FAIL(50007,"对象转换Dto失败"),
    OPENID_CHECK_ERROR(50008,"用户openId检验失败"),
    WECHAT_MP_ERROR(50009,"微信接入错误"),
    PRODUCT_PARAM_CHECK_ERROR(50011,"后台新增商品参数错误"),
    PRODUCT_STATUS_ERROR(50010,"商品上下架状态错误"),
    ORDER_CANCEL_SUCCESS(20001,"后台取消订单成功"),
    ORDER_FINISH_SUCCESS(20002,"后台完结订单成功"),
    USER_PARAM_CHECK_ERROR(50011,"参数校验失败"),
    TELEPHONE_HAS_BE_USED(50012,"电话已被使用"),
    MAIL_HAS_BE_USED(50013,"邮箱已经被使用"),
    ;
    private Integer code;
    private String msg;

    ResultEnum(Integer code,String msg) {
        this.msg = msg;
        this.code = code;
    }
}
