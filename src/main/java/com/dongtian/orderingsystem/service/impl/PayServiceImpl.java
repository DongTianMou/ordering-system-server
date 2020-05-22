package com.dongtian.orderingsystem.service.impl;

import com.dongtian.orderingsystem.dao.OrderInfoRepository;
import com.dongtian.orderingsystem.dto.OrderDto;
import com.dongtian.orderingsystem.enums.PayStatusEnum;
import com.dongtian.orderingsystem.pojo.OrderInfo;
import com.dongtian.orderingsystem.pojo.PaymentInfo;
import com.dongtian.orderingsystem.pojo.PaymentType;
import com.dongtian.orderingsystem.sdk.AcpService;
import com.dongtian.orderingsystem.sdk.LogUtil;
import com.dongtian.orderingsystem.sdk.SDKConstants;
import com.dongtian.orderingsystem.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
@Slf4j
public class PayServiceImpl implements PayService {
    @Autowired
    private YinLianPay yinLianPay;
    @Autowired
    private PaymentTypeService paymentTypeService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderInfoRepository orderInfoRepository;
    @Autowired
    private PaymentInfoService paymentInfoService;

    @Override
    public String pay(PaymentInfo paymentInfo) {
        //拿到支付类型
        Integer typeId = paymentInfo.getPaymentTypeId();
        PaymentType paymentType = paymentTypeService.getByTypeId( typeId );
        if (paymentType == null) {
            return null;
        }
        String typeName = paymentType.getTypeName();
        PayAdaptService payAdaptService=null;
        switch (typeName) {
            case "银联支付":
                payAdaptService = yinLianPay;
                break;

            default:
                break;
        }
        return payAdaptService.pay(paymentInfo, paymentType);
    }

    @Transactional
    @Override
    public String payAsyncallback(HttpServletRequest request) {
        String encoding = request.getParameter(SDKConstants.param_encoding);
        Map<String, String> validData = validData(request, encoding);
        // 重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
        if (!AcpService.validate(validData, encoding)) {
            LogUtil.writeLog("[异步回调]--验证签名结果[失败].");
            // 验签失败，需解决验签问题
        }
        LogUtil.writeLog("[异步回调]--验证签名结果[成功].");
        // 【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
        String orderId = validData.get("orderId"); // 获取后台通知的数据，其他字段也可用类似方式获取

        PaymentInfo paymentInfo = paymentInfoService.findByOrderId(orderId);
        if (paymentInfo == null) {
            log.error("[异步回调]--查询paymentInfo信息失败");
            return "fail";
        }
        // 第三方支付订单号
        paymentInfo.setQryId(validData.get("queryId"));
        // 修改時間
        paymentInfo.setUpdateTime(new Date(  ) );

        paymentInfoService.save(paymentInfo);
        //查看订单详情，修改订单状态
        OrderDto orderDto = orderService.findOneByOrderID(orderId);

        if (orderDto == null) {
            log.error("[异步回调]--查询订单信息失败");
            return "fail";
        }
        Integer state = orderDto.getPayStatus();
        if (state.equals(PayStatusEnum.SUCCESS.getCode())) {
            log.error("订单号:{},已经支付成功!,无需再次做操作..",orderId);
            return "ok";
        }
        // 修改時間
        orderDto.setUpdateTime(new Date(  ) );
        // 修改状态
        orderDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        // 写入订单数据库
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderDto, orderInfo);
        orderInfoRepository.save(orderInfo);
        // 金额 调用中石油充值支付接口--- 延迟 10 15
        // 异步 mq http协议
        return "ok";
    }

    @Override
    public String returnMoney(PaymentInfo paymentInfo) {
        //拿到支付类型
        Integer typeId = paymentInfo.getPaymentTypeId();
        PaymentType paymentType = paymentTypeService.getByTypeId( typeId );
        if (paymentType == null) {
            return null;
        }
        String typeName = paymentType.getTypeName();
        PayAdaptService payAdaptService=null;
        switch (typeName) {
            case "银联支付":
                payAdaptService = yinLianPay;
                break;

            default:
                break;
        }
        return payAdaptService.returnMoney(paymentInfo, paymentType);
    }

    @Override
    public String payReturnAsyncallback(HttpServletRequest req, HttpServletResponse resp) {
        String encoding = req.getParameter(SDKConstants.param_encoding);
        // 获取银联通知服务器发送的后台通知参数
        Map<String, String> reqParam = getAllRequestParam(req);

        Map<String, String> valideData = null;

        if (!reqParam.isEmpty()) {
            Iterator<Map.Entry<String, String>> it = reqParam.entrySet().iterator();
            valideData = new HashMap<String, String>(reqParam.size());
            while (it.hasNext()) {
                Map.Entry<String, String> e = it.next();
                String key = e.getKey();
                String value = e.getValue();
                valideData.put(key, value);
            }
        }
        //重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
        if (!AcpService.validate(valideData, encoding)) {
            LogUtil.writeLog("验证签名结果[失败].");
        } else {
            LogUtil.writeLog("验证签名结果[成功].");
            //【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
            String orderId =valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
            String respCode = valideData.get("respCode");
        }
        LogUtil.writeLog("BackRcvResponse接收后台通知结束");
        //返回给银联服务器http 200  状态
        return "ok";
    }

    private Map<String, String> validData(HttpServletRequest req, String encoding) {
        // 获取银联通知服务器发送的后台通知参数
        Map<String, String> reqParam = getAllRequestParam(req);
        //日志输出
        LogUtil.printRequestLog(reqParam);

        Map<String, String> valideData = null;
        if (!reqParam.isEmpty()) {
            Iterator<Map.Entry<String, String>> it = reqParam.entrySet().iterator();
            valideData = new HashMap<String, String>(reqParam.size());
            while (it.hasNext()) {
                Map.Entry<String, String> e = it.next();
                String key = (String) e.getKey();
                String value = (String) e.getValue();
                try {
                    value = new String(value.getBytes(encoding), encoding);
                } catch (Exception e2) {
                   throw new RuntimeException("银联通知服务器发送的后台通知参数出错");
                }
                valideData.put(key, value);
            }
        }
        return valideData;
    }

    private Map<String, String> getAllRequestParam(HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                // System.out.println("ServletUtil类247行 temp数据的键=="+en+"
                // 值==="+value);
                if (null == res.get(en) || "".equals(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        return res;
    }
}
