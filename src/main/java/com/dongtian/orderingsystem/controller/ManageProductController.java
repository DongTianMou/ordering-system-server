package com.dongtian.orderingsystem.controller;

import com.dongtian.orderingsystem.exceptions.ParamException;
import com.dongtian.orderingsystem.form.ProductForm;
import com.dongtian.orderingsystem.pojo.ProductCategory;
import com.dongtian.orderingsystem.pojo.ProductInfo;
import com.dongtian.orderingsystem.service.CategoryService;
import com.dongtian.orderingsystem.service.ProductService;
import com.dongtian.orderingsystem.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
@Slf4j
@RequestMapping("/manage/product")
public class ManageProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/list")
    public ModelAndView list(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "size",defaultValue = "5") Integer size,
            Map<String,Object> map){
        PageRequest request = PageRequest.of( page-1,size);
        Page<ProductInfo> productInfoPage =  productService.findAll(request);
        map.put("productInfoPage",productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("/product/list",map);
    }

    @GetMapping("/onSale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String, Object> map){
        try {
            productService.onSale(productId);
        }catch (ParamException e){
            map.put("msg", e.getMessage());
            map.put("url", "/ordering-system/manage/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/ordering-system/manage/product/list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/offSale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                                Map<String, Object> map) {
        try {
            productService.offSale(productId);
        } catch(ParamException e){
            map.put("msg", e.getMessage());
            map.put("url", "/ordering-system/manage/product/list");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/ordering-system/manage/product/list");
        return new ModelAndView("common/success", map);
    }

    /*新增商品或者修改商品*/
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                              Map<String, Object> map) {
        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productService.findOne(productId);
            map.put("productInfo", productInfo);
        }
        //查询所有的类目
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);

        return new ModelAndView("product/productFrom", map);
    }
    /*保存表单信息*/
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                             Map<String, Object> map){
        //判断检验结果是否正确
        if (bindingResult.hasErrors()){
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/ordering-system/manage/product/productFrom");
            return new ModelAndView("common/error", map);
        }
        //判断是修改还是新增
        ProductInfo productInfo = new ProductInfo();
        try{
            //如果有productId传过来，说明是修改
            if (!StringUtils.isEmpty(productForm.getProductId())){
                productInfo = productService.findOne(productForm.getProductId());
                productForm.setCreateTime(productInfo.getCreateTime());
            }else {
                //否则新增id
                productForm.setProductId(IdUtils.getUniqueId());
                productForm.setCreateTime(new Date());
            }
            //构建productInfo对象

            productForm.setUpdateTime(new Date());
            BeanUtils.copyProperties(productForm, productInfo);

            productService.save(productInfo);
        }catch (ParamException e){
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/ordering-system/manage/product/productFrom");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/ordering-system/manage/product/list");
        return new ModelAndView("common/success", map);
    }
}
