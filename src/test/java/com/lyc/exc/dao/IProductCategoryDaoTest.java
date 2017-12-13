package com.lyc.exc.dao;

import com.lyc.exc.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lyc94 on 2017/12/4.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IProductCategoryDaoTest {

    @Autowired
    private IProductCategoryDao iProductCategoryDao;

    @Test
    public void findOneTest() {
        ProductCategory productCategory = iProductCategoryDao.findOne(1);
        System.out.println(productCategory.toString());
    }

    @Test
    @Transactional
    public void saveTest() {
        ProductCategory pc = new ProductCategory();
        pc.setCategoryName("lyc");
        pc.setCategoryType(3);
        ProductCategory result = iProductCategoryDao.save(pc);
        Assert.assertNotNull(result);
    }

    @Test
    public void updateTest() {
        ProductCategory pc = new ProductCategory();
        pc.setCategoryId(1);
        pc.setCategoryName("及哈哈");
        pc.setCategoryType(1);
        iProductCategoryDao.save(pc);
    }

    @Test
    public void findByCategoryTypeInTest() {
        List<Integer> list = Arrays.asList(1,2);
        List<ProductCategory> result = iProductCategoryDao.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result.size());
    }
}