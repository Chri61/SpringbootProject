package com.alone.sell.vo;

import com.alone.sell.common.enums.ResponseCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.io.Serializable;

/**
 * 统一接口返回的规范
 *
 * @author Chri61
 * @Date 2021/6/20 0000
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;     //状态
    private String msg;     //消息
    private T data;     //不设置具体类型

    //开始私有构造器
    private ResultVO(int code) {
        this.code = code;
    }

    private ResultVO(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ResultVO(int code, String msg, T data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    private ResultVO(int code, T data) {
        this.code = code;
        this.data = data;
    }

    //对外开放，需要对象调用
    @JsonIgnore     //使之不在json序列化结果当中
    public boolean isSuccess() {
        //状态如果是0，表示成功
        return this.code == ResponseCode.SUCCESS.getCode();
    }

    //对外开放成功的静态方法
    public static <T> ResultVO<T> success() {
        return new ResultVO<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ResultVO<T> successMessage(String msg) {
        return new ResultVO<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> ResultVO<T> success(T data) {
        return new ResultVO<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> ResultVO<T> success(String msg, T data) {
        return new ResultVO<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    //对外开放失败的静态方法
    public static <T> ResultVO<T> error() {
        return new ResultVO<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> ResultVO<T> errorMessage(String msg) {
        return new ResultVO<T>(ResponseCode.ERROR.getCode(), msg);
    }

    //code作为变量的方法
    public static <T> ResultVO<T> errorCodeMessage(int errorCode, String errorMessage) {
        return new ResultVO<T>(errorCode, errorMessage);
    }


}
