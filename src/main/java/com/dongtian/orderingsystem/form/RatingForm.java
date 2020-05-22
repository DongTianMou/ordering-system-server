package com.dongtian.orderingsystem.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
@Data
public class RatingForm {
    @NotEmpty(message = "openId不为空")
    private  String openId;

    @NotEmpty(message = "内容不为空")
    private  String ratingText;

    @NotEmpty(message = "评论类型不为空")
    private String ratingType;

    @NotEmpty(message = "商品id不为空")
    private String productId;
}
