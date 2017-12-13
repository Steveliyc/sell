package com.lyc.exc.dao;

import com.lyc.exc.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lyc94 on 2017/12/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IOrderMasterDaoTest {

    @Autowired
    private IOrderMasterDao iOrderMasterDao;

    private final String OPENID = "111111";

    @Test
    public void saveTest() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("12345");
        orderMaster.setBuyerName("lyc");
        orderMaster.setBuyerPhone("116321");
        orderMaster.setBuyerAddress("创意中央");
        orderMaster.setBuyerOpenid("111111");
        orderMaster.setOrderAmount(new BigDecimal("100"));
        OrderMaster result = iOrderMasterDao.save(orderMaster);

        Assert.assertNotEquals(null, result);
    }

    @Test
    public void findByBuyerOpenid() throws Exception {
        PageRequest pageRequest = new PageRequest(0,5);
        Page<OrderMaster> result = iOrderMasterDao.findByBuyerOpenid(OPENID, pageRequest);
        Assert.assertNotEquals(0, result.getTotalElements());
    }

}