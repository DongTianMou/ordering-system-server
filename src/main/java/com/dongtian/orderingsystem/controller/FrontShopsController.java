package com.dongtian.orderingsystem.controller;

import com.dongtian.orderingsystem.dto.ShopsDto;
import com.dongtian.orderingsystem.service.ShopsService;
import com.dongtian.orderingsystem.utils.ResultVoUtils;
import com.dongtian.orderingsystem.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/front/shops")
public class FrontShopsController extends BaseApiController {
    @Autowired
    private ShopsService shopsService;
    @GetMapping("/info")
    public ResultVo<ShopsDto> info(){
        ShopsDto shopInfo  = shopsService.getOne();
        if (shopInfo != null){
            return ResultVoUtils.success(shopInfo);
        }else
            return ResultVoUtils.error(404,"商家信息为空");
    }
}
