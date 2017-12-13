package com.lyc.exc.enums;

import lombok.Getter;

/**
 * Created by lyc94 on 2017/12/10.
 */
@Getter
public enum ProductStatusEnum {
    UP(0,"上架"),
    DOWN(1,"下架");

    private Integer code;
    private String desc;

    ProductStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
