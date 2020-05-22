package com.dongtian.orderingsystem.service;

import com.dongtian.orderingsystem.pojo.ProductInfo;
import com.dongtian.orderingsystem.service.impl.ProductInfoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductInfoServiceImplTest {
    @Autowired
    private ProductInfoServiceImpl productInfoService;
    @org.junit.Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setCreateTime( new Date() );
        productInfo.setUpdateTime( new Date() );
        productInfo.setProductId( "1" );
        productInfo.setProductName( "皮蛋瘦肉粥" );
        productInfo.setProductIcon("http://fuss10.elemecdn.com/c/cd/c12745ed8a5171e13b427dbc39401jpeg.jpeg?imageView2/1/w/114/h/114");
        productInfo.setProductDescription("很好吃的咸粥");
        productInfo.setProductStock( 100 );
        productInfo.setProductStatus( 0 );
        productInfo.setProductPrice(new BigDecimal(5.0) );
        productInfo.setCategoryType( 2 );
        productInfoService.save( productInfo );

    }

}