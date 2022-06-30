package com.alone.sell.common.exception;

import com.alone.sell.common.enums.ResultEnum;
import lombok.Getter;

/**
 * 自定义异常类
 *
 * @author Chri61
 * @Date 2021/12/30 0030
 */
@Getter
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum exceptionEnum) {
        //把message传到父类的构造方法里面去
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
    }

    public SellException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}
