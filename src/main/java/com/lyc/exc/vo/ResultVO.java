package com.lyc.exc.vo;

import lombok.Data;

/**
 * http 请求返回数据对象
 * Created by lyc94 on 2017/12/10.
 */
@Data
public class ResultVO<T>{

    private Integer code;
    private String msg;
    private T data;
}
