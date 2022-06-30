package com.alone.sell.common.utils;

/**
 * @author Chri61
 * @Date 2022/3/24 0024
 */
public class MathUtil {


    //判断金额是否相等
    public static boolean equals(Double d1, Double d2) {
        if(d1 == null || d2 == null){
            return false;
        }
        double abs = Math.abs(d1 - d2);
        if (abs < 0.01) {
            return true;
        }
        return false;
    }

}
