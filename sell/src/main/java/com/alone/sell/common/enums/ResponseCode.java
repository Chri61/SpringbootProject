package com.alone.sell.common.enums;

//枚举
public enum ResponseCode {
    SUCCESS(0, "SUCCESS"),
    ERROR(1, "ERROR");

    private final int code;
    private final String desc;

    //构造方法
    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    //get方法
    public String getDesc() {
        return desc;
    }
    public int getCode() {
        return code;
    }
}
