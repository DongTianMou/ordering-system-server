package com.dongtian.orderingsystem.controller;

import com.dongtian.orderingsystem.exceptions.ParamException;
import com.dongtian.orderingsystem.form.CategoryForm;
import com.dongtian.orderingsystem.pojo.ProductCategory;
import com.dongtian.orderingsystem.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manage/category")
public class ManageCategoryController {
    @Autowired
    private CategoryService categoryService;
    //查看类目列表
    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map){
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list", map);
    }
    //新增/修改列表
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                              Map<String, Object> map) {
        if (categoryId != null){
            ProductCategory category = categoryService.getOne(categoryId);
            map.put("category", category);
        }
        return new ModelAndView("category/categoryFrom", map);
    }
    //保存修改结果
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/ordering-system/manage/category/index");
            return new ModelAndView("common/error", map);
        }
        ProductCategory productCategory = new ProductCategory();
        try {
            if (form.getCategoryId() != null) {
                productCategory = categoryService.getOne(form.getCategoryId());
                form.setCreateTime(productCategory.getCreateTime());
            }
            form.setCreateTime(new Date());
            form.setUpdateTime(new Date());
            BeanUtils.copyProperties(form, productCategory);
            categoryService.save(productCategory);
        } catch (ParamException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/ordering-system/manage/category/index");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/ordering-system/manage/category/list");
        return new ModelAndView("common/success", map);
    }
}
