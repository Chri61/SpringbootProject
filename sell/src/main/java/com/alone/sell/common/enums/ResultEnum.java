package com.alone.sell.common.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS(1, "成功"),
    PARAM_ERROR(1, "参数错误"),

    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11, "商品库存错误"),
    ORDER_NOT_EXIST(12, "订单不存在"),
    ORDERDETAAL_NOT_EXIST(13, "订单详情不存在"),
    ORDER_STATUS_ERROE(14, "订单状态错误"),
    ORDER_UPDATE_FAIL(15, "订单更新失败"),
    ORDER_DETAIL_EMPTY(16, "订单详情为空"),
    ORDER_PAY_STATUS_ERROR(17, "订单支付状态错误"),
    CART_EMPTY(18, "购物车为空"),
    ORDER_OWNER_ERROE(19, "该订单不属于当前用户"),

    WECHAT_MP_ERROR(20, "微信公众账号错误"),
    WXPAY_NOTIFY_MONEY_VERIFY_ERROR(21, "微信支付异步通知金额校验不通过"),
    ORDER_CANCEL_SUCCESS(22, "订单取消成功"),
    ORDER_FINISH_SUCCESS(23, "订单完结成功"),

    PRODUCT_STATUS_ERROR(24,"商品状态错误"),
    //CATEGORY_TYPE_ERROR(90,"商品种类type重复"),
    LOGIN_FAIL(25, "登录失败"),
    LOGOUT_SUCCESS(26,"登出成功");
    ;
    private int code;
    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
