package com.dongtian.orderingsystem.dto;

import com.dongtian.orderingsystem.enums.UserStatusEnum;
import com.dongtian.orderingsystem.utils.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class UserDto {

    private Integer id ;

    @NotBlank(message = "用户名不可以为空")
    private String username;

    @NotBlank(message = "电话不可以为空")
    private String telephone;

    @NotBlank(message = "邮箱不允许为空")
    @Length(min = 5, max = 50, message = "邮箱长度需要在50个字符以内")
    private String mail;

    @NotNull(message = "必须指定用户的状态")
    private Integer status;

    @JsonIgnore
    public UserStatusEnum getUserStatusEnum() {
        return EnumUtil.getByCode(status, UserStatusEnum.class);
    }
}
