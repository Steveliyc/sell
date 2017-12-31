package com.lyc.exc.service.impl;

import com.alibaba.fastjson.JSON;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lyc.exc.dto.OrderDTO;
import com.lyc.exc.enums.ResultEnum;
import com.lyc.exc.exception.SellException;
import com.lyc.exc.service.OrderService;
import com.lyc.exc.service.PayService;
import com.lyc.exc.utils.BigDecimalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by lyc94 on 2017/12/25.
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "微信点餐订单";

    @Autowired
    private BestPayService bestPayService;
    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付request】 request={}",payRequest);
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付response】 response={}",payResponse);
        return payResponse;
    }

    public PayResponse notify(String notifyData) {
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】异步通知，payResponse={}", JSON.toJSON(payResponse));

        //查询订单
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());
        if(orderDTO == null) {
            log.error("【微信支付】异步通知，订单不存在，orderId={}",payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //判断金额是否一致
        //注意比较方式
        if(BigDecimalUtil.sub(orderDTO.getOrderAmount().doubleValue(), payResponse.getOrderAmount()).doubleValue() != 0) {
        //if(orderDTO.getOrderAmount().compareTo(new BigDecimal(payResponse.getOrderAmount()))!=0) {
            log.error("【微信支付】异步通知，订单金额不一致，orderId={},微信通知金额={}，实际金额={}",
                    payResponse.getOrderId(), payResponse.getOrderAmount(), orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VARIFY_ERROR);
        }

        //修改订单状态
        orderService.paid(orderDTO);

        //1.验证签名
        //2.支付状态
        //3.支付金额
        //4.支付人（下单人 == 支付人）

        return payResponse;
    }

    /**
     * 退款
     * @param orderDTO
     */
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】request={}", refundRequest);
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】response={}", refundResponse);
        return refundResponse;
    }
}
