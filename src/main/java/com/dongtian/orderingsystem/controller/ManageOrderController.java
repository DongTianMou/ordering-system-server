package com.dongtian.orderingsystem.controller;

import com.dongtian.orderingsystem.dto.OrderDto;
import com.dongtian.orderingsystem.enums.ResultEnum;
import com.dongtian.orderingsystem.exceptions.ParamException;
import com.dongtian.orderingsystem.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/manage/order")
@Slf4j
public class ManageOrderController {
    @Autowired
    private OrderService orderService;
    //分页查看订单列表
    @GetMapping("/list")
    public ModelAndView list(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "size",defaultValue = "10") Integer size,
            Map<String,Object> map){
        PageRequest request = PageRequest.of( page-1,size);
        Page<OrderDto> orderDtoPage = orderService.findList(request);
        map.put("orderDTOPage", orderDtoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("/order/list",map);
    }
    //查看订单详情
    @GetMapping("/detail")
    public ModelAndView detail(
            @RequestParam(value = "orderId") String orderId,
            Map<String,Object> map){
        OrderDto orderDto = new OrderDto();
        try{
            orderDto = orderService.findOneByOrderID(orderId);
        }catch (ParamException e){
            log.error("【后台查询订单详情】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/ordering-system/manage/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("orderDTO",orderDto);
        return new ModelAndView("/order/detail",map);
    }
    //取消订单
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        try {
            OrderDto orderDTO = orderService.findOneByOrderID(orderId);
            orderService.cancel(orderDTO);
        } catch (ParamException e) {
            log.error("【后台取消订单】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/ordering-system/manage/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMsg());
        map.put("url", "/ordering-system/manage/order/list");
        return new ModelAndView("common/success");
    }
    //完成订单
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId,
                               Map<String, Object> map){
        try {
            OrderDto orderDTO = orderService.findOneByOrderID(orderId);
            orderService.finish(orderDTO);
        } catch (ParamException e) {
            log.error("【后台完结订单】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/ordering-system/manage/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMsg());
        map.put("url", "/ordering-system/manage/order/list");
        return new ModelAndView("common/success");
    }
}
