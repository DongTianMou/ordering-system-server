package com.dongtian.orderingsystem.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity(name="payment_type")
public class PaymentType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer typeId = 1;
	/**
	 * 支付平台
	 */
	private String typeName;
	/**
	 * 同步通知
	 */
	private String frontUrl;
	/**
	 * 同步通知
	 */
	private String backUrl;
	/**
	 * 商户id
	 */
	private String merchantId;
	/**
	 * 创建时间
	 */
	private Date create_time;
	/**
	 * 修改时间
	 */
	private Date update_time;
}