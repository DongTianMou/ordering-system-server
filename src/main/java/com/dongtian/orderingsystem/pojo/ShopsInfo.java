package com.dongtian.orderingsystem.pojo;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name="shops_info")
@Proxy(lazy = false)
public class ShopsInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = 1;
    // 商家名字
    private String name;
    // 商家描述
    private String description;
    // 外卖配送时间
    private Integer deliveryTime;
    // 商店评分
    private Double score = 5.0;
    // 服务评分
    private Double serviceScore = 5.0;
    // 食品评分
    private Double foodScore = 5.0;
    // 好评率
    private Double rankRate = 100.0;
    // 销售最低价格
    private Double minPrice;
    // 外卖配送价格
    private Double deliveryPrice;
    // 评价总数
    private Integer ratingCount = 0;
    // 销售总数
    private Integer sellCount = 0;
    // 公告
    private String bulletin;
    // 商店头像
    private String avatar;
    // 商店地址
    private String address;
    // 营业时间
    private String openingHours;

}
