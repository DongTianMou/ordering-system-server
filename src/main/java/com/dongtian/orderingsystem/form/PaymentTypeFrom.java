package com.dongtian.orderingsystem.form;

import lombok.Data;

@Data
public class PaymentTypeFrom {
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
}
