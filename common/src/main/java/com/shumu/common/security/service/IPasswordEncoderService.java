package com.shumu.common.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
/**
* @description: 获取代理PasswordEncoder
* @author: Li
* @date: 2023-01-11
*/
public interface IPasswordEncoderService {
    /**
     * 获取代理PasswordEncoder
     * @return PasswordEncoder
     */
    public PasswordEncoder delegatingPasswordEncoder();
}
