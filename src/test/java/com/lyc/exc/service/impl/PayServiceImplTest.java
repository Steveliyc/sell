package com.lyc.exc.service.impl;

import com.lly835.bestpay.model.PayResponse;
import com.lyc.exc.dto.OrderDTO;
import com.lyc.exc.service.OrderService;
import com.lyc.exc.service.PayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by lyc94 on 2017/12/25.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PayServiceImplTest {

    @Autowired
    PayService payService;
    @Autowired
    OrderService orderService;

    @Test
    public void create() throws Exception {
        OrderDTO orderDTO = orderService.findOne("12345");
       PayResponse payResponse =  payService.create(orderDTO);
    }

    @Test
    public void refundTest() {
        OrderDTO orderDTO = orderService.findOne("12345");
        payService.refund(orderDTO);
    }
}