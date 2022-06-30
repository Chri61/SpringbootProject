package com.alone.sell.common.utils;

import java.util.Random;

/**
 * @author Chri61
 * @Date 2021/12/31 0031
 */
public class KeyUtil {

    /**
     * 生成唯一主键
     *
     * @return
     */
    public static synchronized String getUniqueKey() {
        Random random = new Random();
        Integer i = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + i.toString();
    }

}
