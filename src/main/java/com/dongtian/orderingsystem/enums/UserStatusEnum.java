package com.dongtian.orderingsystem.enums;

import lombok.Getter;

@Getter
public enum UserStatusEnum implements CodeEnum{
    NORMAL(2, "用户状态正常"),
    ABNORMAL(1, "用户状态不正常")
    ;

    private Integer code;
    private String message;

    UserStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
