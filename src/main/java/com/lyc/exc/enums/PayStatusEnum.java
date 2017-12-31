package com.lyc.exc.enums;

import lombok.Getter;

/**
 * Created by lyc94 on 2017/12/13.
 */
@Getter
public enum PayStatusEnum {
    WAIT(0,"未支付"),
    SUCCESS(1,"支付成功");

    private Integer code;
    private String desc;

    PayStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
