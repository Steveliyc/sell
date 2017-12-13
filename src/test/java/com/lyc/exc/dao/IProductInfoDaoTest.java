package com.lyc.exc.dao;

import com.lyc.exc.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lyc94 on 2017/12/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IProductInfoDaoTest {

    @Autowired
    private IProductInfoDao iProductInfoDao;

    @Test
    public void saveTest() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("茶叶蛋");
        productInfo.setProductPrice(new BigDecimal("2"));
        productInfo.setProductStock(100);
        productInfo.setProductStatus(0);
        productInfo.setProductIcon("http://xxx.egg.jpg");
        productInfo.setCategoryType(3);

        ProductInfo result = iProductInfoDao.save(productInfo);
        Assert.assertNotEquals(null, result);
    }

    @Test
    public void findByProductStatus() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductStatus(0);
        List<ProductInfo> resultList = iProductInfoDao.findByProductStatus(0);
        Assert.assertNotEquals(0, resultList.size());
    }


}