package com.shumu.common.security.provider;

import java.util.List;

import cn.hutool.jwt.JWT;
/**
* @Description: 
* @Author: Li
* @Date: 2022-03-12
* @LastEditTime: 2022-03-12
* @LastEditors: Li
*/
public class JwtTokenProvider {

    public String createToken(String username, List<String> authorities) {
        byte[] key = "1234567890".getBytes();
        String token = JWT.create()
                .setPayload("username", username)
                .setPayload("authorities", authorities)
                .setKey(key)
                .sign();
        return token;
    }

    
}
