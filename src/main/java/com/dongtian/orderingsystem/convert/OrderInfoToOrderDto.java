package com.dongtian.orderingsystem.convert;

import com.dongtian.orderingsystem.dto.OrderDto;
import com.dongtian.orderingsystem.pojo.OrderInfo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderInfoToOrderDto {

    public static OrderDto convert(OrderInfo orderInfo) {
        OrderDto orderDto  = new OrderDto();
        BeanUtils.copyProperties( orderInfo,orderDto);
        return orderDto;
    }

    public static List<OrderDto> convert(List<OrderInfo> orderInfos) {
        return orderInfos.stream().map(e ->
                convert(e)
        ).collect( Collectors.toList());
    }
}
