package com.dongtian.orderingsystem.controller;

import com.dongtian.orderingsystem.dto.OrderDto;
import com.dongtian.orderingsystem.enums.ResultEnum;
import com.dongtian.orderingsystem.exceptions.ParamException;
import com.dongtian.orderingsystem.pojo.PaymentInfo;
import com.dongtian.orderingsystem.service.OrderService;
import com.dongtian.orderingsystem.service.PayService;
import com.dongtian.orderingsystem.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/frontPay")
public class PayController extends BaseApiController{
    @Autowired
    private OrderService orderService;
    @Autowired
    private PayService payService;
    @Autowired
    private PaymentInfoService paymentInfoService;
    @RequestMapping("/pay")
    public void pay(
            @RequestParam("orderId") String orderId,
            HttpServletResponse resp
    ) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        //1. 查询订单
        OrderDto orderDto = orderService.findOneByOrderID(orderId);
        if (orderDto == null) {
            resp.getWriter().write("系统错误，订单为空!");
            throw new ParamException(ResultEnum.ORDER_NOT_EXIST);
        }

        PaymentInfo paymentInfo = paymentInfoService.create(orderDto);

        PrintWriter out=resp.getWriter();
        //2.发起支付
        String html = payService.pay(paymentInfo);
        out.println(html);
        out.close();
    }

    @RequestMapping("/returnMoney")
    public void returnMoney(
            @RequestParam("orderId") String orderId) throws IOException {
        ///1. 查询订单
        OrderDto orderDto = orderService.findOneByOrderID(orderId);
        if (orderDto == null) {
            throw new ParamException(ResultEnum.ORDER_NOT_EXIST);
        }

        PaymentInfo paymentInfo = paymentInfoService.findByOrderId(orderId);
        //2.发起退款
        String respCode = payService.returnMoney(paymentInfo);
        System.out.println(respCode);
    }

    @RequestMapping("/asyn")
    public String asynCallback(HttpServletRequest req) {
        return payService.payAsyncallback(req);
    }
}
