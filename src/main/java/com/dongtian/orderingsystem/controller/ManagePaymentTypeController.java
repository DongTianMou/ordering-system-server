package com.dongtian.orderingsystem.controller;

import com.dongtian.orderingsystem.exceptions.ParamException;
import com.dongtian.orderingsystem.pojo.PaymentType;
import com.dongtian.orderingsystem.service.PaymentTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manage/paymentType")
public class ManagePaymentTypeController {
    @Autowired
    private PaymentTypeService typeService;

    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map){
        List<PaymentType> paymentTypes = typeService.findAll();
        map.put("paymentTypes", paymentTypes);
        return new ModelAndView("paymentType/list", map);
    }
    //新增/修改列表
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "typeId", required = false) Integer typeId,
                              Map<String, Object> map) {
        if (typeId != null){
            PaymentType paymentType = typeService.getByTypeId(typeId);
            map.put("paymentType", paymentType);
        }
        return new ModelAndView("paymentType/paymentTypeFrom", map);
    }

    @PostMapping("/save")
    public ModelAndView add(@Valid PaymentType form,
                      BindingResult bindingResult,
                      Map<String, Object> map){
        //判断检验结果是否正确
        if (bindingResult.hasErrors()){
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/ordering-system/manage/paymentType/list");
            return new ModelAndView("common/error", map);
        }
        //判断是修改还是新增
        PaymentType paymentType = new PaymentType();
        try{
            //如果有Id传过来，说明是修改
            if (!StringUtils.isEmpty(form.getTypeId())){
                paymentType = typeService.getByTypeId(form.getTypeId());
                form.setCreate_time(paymentType.getCreate_time());
            }
            //构建对象
            form.setCreate_time(new Date());
            form.setUpdate_time(new Date());
            BeanUtils.copyProperties(form, paymentType);

            typeService.save(paymentType);

        }catch (ParamException e){
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/ordering-system/manage/paymentType/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/ordering-system/manage/paymentType/list");
        return new ModelAndView("common/success", map);
    }

}
