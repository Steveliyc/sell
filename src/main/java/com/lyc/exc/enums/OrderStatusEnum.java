package com.lyc.exc.enums;

import lombok.Getter;

/**
 * Created by lyc94 on 2017/12/13.
 */
@Getter
public enum OrderStatusEnum {
    NEW(0,"新订单"),
    FINISHED(1,"完结"),
    CANCLE(2,"已取消");

    private Integer code;
    private String desc;

    OrderStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
