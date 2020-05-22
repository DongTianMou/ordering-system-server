package com.dongtian.orderingsystem.categoryTest;

import com.dongtian.orderingsystem.pojo.ProductCategory;
import com.dongtian.orderingsystem.service.impl.CategoryServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryTest {
    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName( "热销榜" );
        productCategory.setCategoryType( 1 );
        productCategory.setCreateTime(new Date() );
        productCategory.setUpdateTime( new Date(  ) );
        categoryService.save( productCategory );
    }
    @Test
    public void getOneTest(){
        ProductCategory productCategory = categoryService.getOne( 1 );
        int type = productCategory.getCategoryType();
        System.out.println(type);
    }

    @Test
    public void findByCategoryTypeInTest() {
        List<Integer> list = Arrays.asList(1,2);
        List<ProductCategory> result = categoryService.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0, result.size());
        System.out.println(result);
    }
}
