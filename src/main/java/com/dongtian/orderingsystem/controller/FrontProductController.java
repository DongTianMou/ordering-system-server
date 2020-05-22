package com.dongtian.orderingsystem.controller;

import com.dongtian.orderingsystem.pojo.ProductCategory;
import com.dongtian.orderingsystem.pojo.ProductInfo;
import com.dongtian.orderingsystem.pojo.Rating;
import com.dongtian.orderingsystem.service.CategoryService;
import com.dongtian.orderingsystem.service.ProductService;
import com.dongtian.orderingsystem.service.RatingService;
import com.dongtian.orderingsystem.utils.ResultVoUtils;
import com.dongtian.orderingsystem.vo.ProductInfoVO;
import com.dongtian.orderingsystem.vo.ProductVO;
import com.dongtian.orderingsystem.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/front/product")
/*获取商品列表，返回json格式
*  {
*    "code": 0,
*    "msg": "成功",
*    "data": []
*  }
* */
public class FrontProductController extends BaseApiController{
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RatingService ratingService;

    @GetMapping("/list")
    public ResultVo list() {
        //1. 查询所有的上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();
        //2. 查询类目(一次性查询): 拿到type,通过type查询ProductCategory
        List<Integer> categoryTypeList = productInfoList.stream()
                .map( e->e.getCategoryType() )
                .collect( Collectors.toList() );
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn( categoryTypeList );
        //3. 数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory: productCategoryList) {
            //设置productVO,类目设置
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());
            //设置productInfoVO,商品详情的设置
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();

            for (ProductInfo productInfo: productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    //使用BeanUtils,字段相同进行复制
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    List<Rating> ratings = ratingService.findByProductId(productInfoVO.getProductId());
                    productInfoVO.setRatings(ratings);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        return ResultVoUtils.success( productVOList );
    }

}
