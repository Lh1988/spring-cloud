package com.shumu.common.security.exception;

import org.springframework.security.core.AuthenticationException;
/**
* @Description: 
* @Author: Li
* @Date: 2022-02-16
* @LastEditTime: 2022-02-16
* @LastEditors: Li
*/
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
    
}
