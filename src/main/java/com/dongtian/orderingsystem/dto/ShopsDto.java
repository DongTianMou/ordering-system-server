package com.dongtian.orderingsystem.dto;

import com.dongtian.orderingsystem.pojo.Support;
import lombok.Data;

import java.util.List;
@Data
public class ShopsDto {
    private Integer id = 1;
    // 商家名字
    private String name;
    // 商家描述
    private String description;
    // 外面配送时间
    private Integer deliveryTime;
    // 商店评分
    private Double score;
    // 服务评分
    private Double serviceScore;
    // 食品评分
    private Double foodScore ;
    // 好评率
    private Double rankRate;
    // 销售最低几个
    private Double minPrice;
    // 外面配送价格
    private Double deliveryPrice;
    // 评价综述
    private Integer ratingCount;
    // 销售总数
    private Integer sellCount;
    // 公告
    private String bulletin;
    // 商店头像
    private String avatar;
    // 商店地址
    private String address;
    // 营业时间
    private String OpeningHours;

    private List<Support> supports;
}
