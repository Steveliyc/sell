package com.lyc.exc.service.impl;

import com.lyc.exc.dto.OrderDTO;
import com.lyc.exc.enums.ResultEnum;
import com.lyc.exc.exception.SellException;
import com.lyc.exc.service.BuyerService;
import com.lyc.exc.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lyc94 on 2017/12/17.
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService{

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openId, String orderId) {

        return checkOrderOwner(openId, orderId);
    }

    @Override
    public OrderDTO cacnleOrder(String openId, String orderId) {
       OrderDTO orderDTO = checkOrderOwner(openId, orderId);
       if(orderDTO == null) {
           log.error("【取消订单】查不到该订单，orderId={}", orderId);
           throw new SellException(ResultEnum.ORDER_NOT_EXIST);
       }
       return orderService.cancle(orderDTO);
    }

    private OrderDTO checkOrderOwner(String openId, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null) {
            return null;
        }
        //判断是否是自己的订单
        if(!orderDTO.getBuyerOpenid().equals(openId)) {
            log.error("【查询订单】订单openId不一致,openId={}, orderDTO={}",openId,orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
