package com.alone.sell.common.utils;

import com.alone.sell.common.enums.CodeEnum;

/**
 * @author Chri61
 * @Date 2022/3/25 0025
 */
public class EnumUtil {

    /**
     * 传入 code 和枚举类
     * @param code
     * @param enumClass
     * @param <T>
     * @return
     */
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass){
        for (T econs : enumClass.getEnumConstants()) {
            if(code.equals(econs.getCode())){
                return  econs;
            }
        }
        return null;
    }
}
