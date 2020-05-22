package com.dongtian.orderingsystem.controller;

import com.dongtian.orderingsystem.exceptions.ParamException;
import com.dongtian.orderingsystem.pojo.ShopsInfo;
import com.dongtian.orderingsystem.service.ShopsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequestMapping("/manage/shops")
@Controller
public class ManageShopsController {
    @Autowired
    private ShopsService shopsService;
    @GetMapping("/info")
    public ModelAndView list(Map<String, Object> map){
        List<ShopsInfo> shopsInfos = shopsService.findAll();
        map.put("shopsInfos", shopsInfos);
        return new ModelAndView("shops/info", map);
    }
    //新增/修改列表
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "id", required = false) Integer id,
                              Map<String, Object> map) {
        if (id != null){
            ShopsInfo shops = shopsService.getOne(id);
            map.put("shops", shops);
        }
        return new ModelAndView("shops/shopsFrom", map);
    }

    @PostMapping("/save")
    public ModelAndView add(@Valid ShopsInfo form,
                            BindingResult bindingResult,
                            Map<String, Object> map){
        //判断检验结果是否正确
        if (bindingResult.hasErrors()){
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/ordering-system/manage/shops/info");
            return new ModelAndView("common/error", map);
        }
        //判断是修改还是新增
        ShopsInfo shopsInfo = new ShopsInfo();
        try{
            //如果有Id传过来，说明是修改
            if (!StringUtils.isEmpty(form.getId())){
                shopsInfo = shopsService.getOne(form.getId());

            }
            BeanUtils.copyProperties(form, shopsInfo);

            shopsService.save(shopsInfo);

        }catch (ParamException e){
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/ordering-system/manage/shops/info");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/ordering-system/manage/shops/info");
        return new ModelAndView("common/success", map);
    }
}
