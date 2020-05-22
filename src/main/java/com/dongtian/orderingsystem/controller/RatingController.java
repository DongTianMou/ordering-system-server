package com.dongtian.orderingsystem.controller;

import com.dongtian.orderingsystem.pojo.Rating;
import com.dongtian.orderingsystem.service.RatingService;
import com.dongtian.orderingsystem.service.SensitiveService;
import com.dongtian.orderingsystem.utils.ResultVoUtils;
import com.dongtian.orderingsystem.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/front/rating")
@Slf4j
public class RatingController extends BaseApiController{
    @Autowired
    private RatingService ratingService;
    @Autowired
    private SensitiveService sensitiveService;
    @GetMapping("/create")
    public ResultVo save(
            @RequestParam( value = "openid") String openid,
            @RequestParam( value = "score") String score,
            @RequestParam( value = "productId") String productId,
            @RequestParam( value = "ratingType") Integer ratingType,
            @RequestParam( value = "ratingText") String ratingText
            ){
        String text = HtmlUtils.htmlEscape(ratingText);
        text = sensitiveService.filter(text);
        Rating rating = Rating.builder().id(1).score(score).
                openId(openid).productId(productId).
                rateType(ratingType).text(text).rateTime(new Date()).build();
        ratingService.save(rating);
        return ResultVoUtils.success();
    }
    @GetMapping("/list")
    public ResultVo<List<Rating>> ratingList(){
        List<Rating> ratings = ratingService.findAll( );
        return ResultVoUtils.success(ratings);
    }

}
