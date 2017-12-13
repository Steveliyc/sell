package com.lyc.exc.utils;

import com.lyc.exc.vo.ResultVO;

/**
 * Created by lyc94 on 2017/12/10.
 */
public class ResultVOUtil {

    public static ResultVO success(Object obj) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(obj);
        resultVO.setCode(0);
        resultVO.setMsg("SCCESS");
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
