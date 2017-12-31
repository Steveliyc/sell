package com.lyc.exc.service;

import com.lyc.exc.dto.OrderDTO;

/**
 * 买家
 * Created by lyc94 on 2017/12/17.
 */
public interface BuyerService {

    OrderDTO findOrderOne(String openId, String orderId);

    OrderDTO cacnleOrder(String openId, String orderId);
}
