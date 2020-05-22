package com.dongtian.orderingsystem.vo;

import lombok.Data;

@Data
public class ResultVo<T> {
    //状态码
    private Integer code;
    //消息
    private String msg;
    //数据域
    private T data;
}
