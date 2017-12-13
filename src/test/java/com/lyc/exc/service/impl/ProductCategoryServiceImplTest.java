package com.lyc.exc.service.impl;

import com.lyc.exc.dataobject.ProductCategory;
import com.lyc.exc.service.ProductCategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lyc94 on 2017/12/6.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    public void findOne() throws Exception {
        ProductCategory productCategory = productCategoryService.findOne(1);
        Assert.assertEquals(new Integer(1), productCategory.getCategoryId());
    }


    @Test
    public void findAll() throws Exception {
       List<ProductCategory> productCategoryList =  productCategoryService.findAll();
        Assert.assertNotEquals(0, productCategoryList.size());
    }

    @Test
    public void findByCategoryTypeIn() throws Exception {
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(Arrays.asList(1,2,3,4));
        Assert.assertNotEquals(0, productCategoryList.size());
}

    @Test
    public void save() throws Exception {
        ProductCategory pc = new ProductCategory();
        //pc.setCategoryId(1);
        pc.setCategoryName("及哈哈");
        pc.setCategoryType(3);
        ProductCategory productCategory = productCategoryService.save(pc);
        Assert.assertNotEquals(null, productCategory);

    }

}