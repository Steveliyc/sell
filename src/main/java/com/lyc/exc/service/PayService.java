package com.lyc.exc.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.lyc.exc.dto.OrderDTO;

/**
 * 支付service
 * Created by lyc94 on 2017/12/25.
 */
public interface PayService {

    PayResponse create(OrderDTO orderDTO);

    public PayResponse notify(String notifyData);

    RefundResponse refund(OrderDTO orderDTO);
}
