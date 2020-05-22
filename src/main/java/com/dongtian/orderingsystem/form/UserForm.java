package com.dongtian.orderingsystem.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserForm {
    @NotEmpty(message = "用户名必填")
    private String username;

    @NotEmpty(message = "密码必填")
    private String password;

    @NotEmpty(message = "电话必填")
    private String telephone;

}
