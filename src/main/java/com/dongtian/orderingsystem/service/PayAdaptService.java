package com.dongtian.orderingsystem.service;

import com.dongtian.orderingsystem.pojo.PaymentInfo;
import com.dongtian.orderingsystem.pojo.PaymentType;
/*为了程序的可扩展性，新建一个接口，通过传来的支付类型调用不同的实现*/
public interface PayAdaptService {
    String pay(PaymentInfo paymentInfo, PaymentType paymentType);

    String returnMoney(PaymentInfo paymentInfo, PaymentType paymentType);
}
