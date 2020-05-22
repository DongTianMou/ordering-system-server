package com.dongtian.orderingsystem.exceptions;

import com.dongtian.orderingsystem.enums.ResultEnum;

public class ParamException extends RuntimeException {
    private Integer code;

    public ParamException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public ParamException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
