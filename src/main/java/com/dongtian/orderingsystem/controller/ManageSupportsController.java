package com.dongtian.orderingsystem.controller;

import com.dongtian.orderingsystem.exceptions.ParamException;
import com.dongtian.orderingsystem.pojo.Support;
import com.dongtian.orderingsystem.service.SupportsService;
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
@RequestMapping("/manage/supports")
@Controller
public class ManageSupportsController {
    @Autowired
    private SupportsService supportsService;
    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map){
        List<Support> supports = supportsService.findAll();
        map.put("supports", supports);
        return new ModelAndView("supports/list", map);
    }
    //新增/修改列表
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "id", required = false) Integer id,
                              Map<String, Object> map) {
        if (id != null){
            Support support = supportsService.getOne(id);
            map.put("supports", support);
        }
        return new ModelAndView("supports/supportsFrom", map);
    }

    @PostMapping("/save")
    public ModelAndView add(@Valid Support form,
                            BindingResult bindingResult,
                            Map<String, Object> map){
        //判断检验结果是否正确
        if (bindingResult.hasErrors()){
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/ordering-system/manage/supports/list");
            return new ModelAndView("common/error", map);
        }
        //判断是修改还是新增
        Support support = new Support();
        try{
            //如果有Id传过来，说明是修改
            if (!StringUtils.isEmpty(form.getId())){
                support = supportsService.getOne(form.getId());

            }
            BeanUtils.copyProperties(form, support);

            supportsService.save(support);

        }catch (ParamException e){
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/ordering-system/manage/supports/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/ordering-system/manage/supports/list");
        return new ModelAndView("common/success", map);
    }
}
