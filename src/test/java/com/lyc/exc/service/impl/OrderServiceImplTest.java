package com.lyc.exc.service.impl;

import com.lyc.exc.dataobject.OrderDetail;
import com.lyc.exc.dto.OrderDTO;
import com.lyc.exc.enums.OrderStatusEnum;
import com.lyc.exc.enums.PayStatusEnum;
import com.lyc.exc.service.OrderService;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lyc94 on 2017/12/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    private final String BUYER_OPENID = "111111";

    @Test
    public void create() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("创意中央");
        orderDTO.setBuyerName("lyc");
        orderDTO.setBuyerOpenid(BUYER_OPENID);
        orderDTO.setBuyerPhone("12345678");
        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId("12346");
        orderDetail.setProductId("123457");
        orderDetail.setProductQuantity(4);

        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setOrderId("12346");
        orderDetail2.setProductId("123456");
        orderDetail2.setProductQuantity(3);
        orderDetailList.add(orderDetail);
        orderDetailList.add(orderDetail2);
        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result = orderService.create(orderDTO);
        log.info("【创建订单】result={}",result);
    }

    @Test
    public void findOne() throws Exception {
        OrderDTO orderDTO = orderService.findOne("15134981783081661239");
        log.info("【查询订单】orderDto={}",orderDTO);
    }

    @Test
    public void findList() throws Exception {
        PageRequest pageRequest = new PageRequest(0,5);
        Page<OrderDTO> result = orderService.findList("111111", pageRequest);
        Assert.assertNotEquals(0, result.getTotalElements());
        log.info("[page] result={}",result.getContent());
    }

    @Test
    public void cancle() throws Exception {
        OrderDTO orderDTO = orderService.findOne("15134979336861805292");
        OrderDTO result = orderService.cancle(orderDTO);
        Assert.assertEquals(orderDTO.getOrderId() , result.getOrderId());
    }

    @Test
    public void finish() throws Exception {
        OrderDTO orderDTO = orderService.findOne("15134979336861805292");
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), result.getOrderStatus());
    }

    @Test
    public void paid() throws Exception {
        OrderDTO orderDTO = orderService.findOne("15134979336861805292");
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getPayStatus());
    }

}