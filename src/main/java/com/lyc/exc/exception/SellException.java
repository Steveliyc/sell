package com.lyc.exc.exception;

import com.lyc.exc.enums.ResultEnum;

/**
 * Created by lyc94 on 2017/12/16.
 */
public class SellException extends RuntimeException{
    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(int code, String message) {
        super(message);
        this.code = code;
    }
}
