package com.alone.sell.common.enums;

import lombok.Getter;

//支付状态
@Getter
public enum PayStatusEnum implements CodeEnum {
    WAIT(0, "未支付"),
    SUCCESS(1, "支付成功"),
    ;

    private Integer code;
    private String msg;

    PayStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
