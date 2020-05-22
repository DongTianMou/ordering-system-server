package com.dongtian.orderingsystem.pojo;

import com.dongtian.orderingsystem.enums.OrderStatusEnum;
import com.dongtian.orderingsystem.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/*
* 订单主表
*/
@Data
@Entity(name="order_info")
@Proxy(lazy = false)
public class OrderInfo {
    /** 订单id. */
    @Id
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    /** 卖家微信Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    private Date createTime;

    private Date updateTime;
}