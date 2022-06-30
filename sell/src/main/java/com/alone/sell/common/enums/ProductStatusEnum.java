package com.alone.sell.common.enums;

import lombok.Getter;

//商品的状态
@Getter
public enum ProductStatusEnum implements CodeEnum {
    UP(0, "上架中"),
    DOWN(1, "下架");

    private Integer code;
    private String msg;

    ProductStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}