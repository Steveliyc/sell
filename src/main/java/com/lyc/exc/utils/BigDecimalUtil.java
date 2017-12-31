package com.lyc.exc.utils;

import java.math.BigDecimal;

/**
 * Created by lyc94 on 2017/12/27.
 */
public class BigDecimalUtil {

    private BigDecimalUtil() {}

    public static BigDecimal add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }

    public static BigDecimal sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2);
    }

    public static BigDecimal mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }

    public static BigDecimal div(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);//四舍五入，保留2位小数
    }

    public static void main(String[] args) {
        BigDecimal b1 = new BigDecimal("0.1");
        double d = 0.10;
        System.out.println(BigDecimalUtil.sub(b1.doubleValue(),d).doubleValue()==0);
    }
}
