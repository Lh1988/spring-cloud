package com.shumu.common.base.response;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.Data;

/**
* @Description: 
* @Author: Li
* @Date: 2021-12-29
* @LastEditTime: 2021-12-29
* @LastEditors: Li
*/
@Data
public class BaseResponse<T> implements Serializable {
    private Integer code=200;

    private boolean success = true;

    private String message = "操作成功!";

    private long timestamp = System.currentTimeMillis();

    private T result;
    
    public static BaseResponse<Object> response(Object result, String msg, int code, boolean success) {
        BaseResponse<Object> response = new BaseResponse<>();
        response.setCode(code);
        response.setMessage(msg);
        response.setResult(result);
        response.setSuccess(success);
        return response;
    }
    
    public static BaseResponse<Object> error(String msg,int code) {
        return response(null,msg,code,false);
    }

    public static BaseResponse<Object> error(String msg) {
        return error(msg, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public static BaseResponse<Object> error() {
        return error("操作失败!");
    }

    public static BaseResponse<Object> ok(Object result,String msg) {
        return response(result,msg,200,true);
    }

    public static BaseResponse<Object> ok(Object result) {
        return ok(result,"操作成功!");
    }

    public static BaseResponse<Object> ok(String msg) {
        return ok(null,msg);
    }

    public static BaseResponse<Object> ok() {
        return ok(null);
    }
}
