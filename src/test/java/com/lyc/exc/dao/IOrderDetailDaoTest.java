package com.lyc.exc.dao;

import com.lyc.exc.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lyc94 on 2017/12/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IOrderDetailDaoTest {

    @Autowired
    private IOrderDetailDao iOrderDetailDao;

    @Test
    public void saveTest() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId("12345");
        orderDetail.setDetailId("54321");
        orderDetail.setProductId("123");
        orderDetail.setProductName("茶叶蛋");
        orderDetail.setProductQuantity(2);
        orderDetail.setProductPrice(new BigDecimal("2.5"));
        orderDetail.setProductIcon("http://1243.jpg");

        OrderDetail result = iOrderDetailDao.save(orderDetail);
        Assert.assertNotEquals(null, result);
    }


    @Test
    public void findByOrderId() throws Exception {
        List<OrderDetail> list = iOrderDetailDao.findByOrderId("12345");
        Assert.assertNotEquals(0, list.size());
    }

}