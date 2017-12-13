package com.lyc.exc.service.impl;

import com.lyc.exc.dataobject.ProductInfo;
import com.lyc.exc.service.ProductInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lyc94 on 2017/12/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoService productInfoService;

    @Test
    public void findOne() throws Exception {
        ProductInfo productInfo = productInfoService.findOne("123456");
        Assert.assertEquals("123456", productInfo.getProductId());
    }

    @Test
    public void findUpAll() throws Exception {
        List<ProductInfo> result = productInfoService.findUpAll();
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void findAll() throws Exception {

        PageRequest pageRequest = new PageRequest(0, 2);
        Page<ProductInfo> page = productInfoService.findAll(pageRequest);
        System.out.println(page.getTotalElements());

    }

    @Test
    @Transactional  //此注解不会在Test测试里面不会提交数据到后台
    public void save() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("豆浆");
        productInfo.setProductPrice(new BigDecimal("3"));
        productInfo.setProductStock(100);
        productInfo.setProductStatus(0);
        productInfo.setProductIcon("http://xxx.doujiang.jpg");
        productInfo.setCategoryType(3);

        ProductInfo result = productInfoService.save(productInfo);
        Assert.assertNotEquals(null, result);
    }

}