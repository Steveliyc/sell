package com.lyc.exc.enums;

import lombok.Getter;

/**
 * Created by lyc94 on 2017/12/16.
 */
@Getter
public enum ResultEnum {

    PARAM_ERROR(1,"参数不正确"),
    CART_EMPTY(2,"购物车为空"),
    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"库存有误"),
    ORDER_NOT_EXIST(12,"订单不存在"),
    ORDERDETAIL_NOT_EXIST(13,"订单详情不存在"),
    ORDER_STATUS_ERROR(14,"订单状态有误"),
    ORDER_UPDATE_ERROR(15,"订单更新有误"),
    ORDER_STOCK_EMPTY(16,"订单为空"),
    ORDER_PAY_STATUS_ERROR(17,"支付状态错误"),
    ORDER_OWNER_ERROR(18,"订单所属有误"),
    WECHART_MP_ERROR(19,"微信网页授权异常"),
    WXPAY_NOTIFY_MONEY_VARIFY_ERROR(20,"微信异步通知金额校验不通过"),
    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
