package com.dongtian.orderingsystem.form;

import lombok.Data;

import java.util.Date;

@Data
public class CategoryForm {

    private Integer categoryId;

    /** 类目名字. */
    private String categoryName;

    /** 类目编号. */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;;
}
