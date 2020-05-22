package com.dongtian.orderingsystem.service;

import com.dongtian.orderingsystem.pojo.PaymentInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PayService {
    String pay(PaymentInfo paymentInfo);
    /*异步回调通知*/
    String payAsyncallback(HttpServletRequest request);
    /*用户取消订单，退款*/
    String returnMoney(PaymentInfo paymentInfo);

    String payReturnAsyncallback(HttpServletRequest request, HttpServletResponse response);
}
