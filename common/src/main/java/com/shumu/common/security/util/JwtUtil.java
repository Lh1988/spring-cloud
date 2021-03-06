package com.shumu.common.security.util;


import javax.servlet.http.HttpServletRequest;

import com.shumu.common.security.constant.SecurityConstant;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
/**
* @Description: JWT工具
* @Author: Li
* @Date: 2022-03-13
* @LastEditTime: 2022-03-13
* @LastEditors: Li
*/
public class JwtUtil {
    /** Token过期时间30分钟（用户登录过期时间是此时间的两倍，以token在reids缓存时间为准）*/
	public static final long EXPIRE_TIME = 30 * 60 * 1000;

    public static String createToken(byte[] key, String userId, String[] authorities) {
        String token = JWT.create()
        .setNotBefore(DateUtil.offsetSecond(DateUtil.date(), (int) (EXPIRE_TIME/1000)))
        .setPayload(SecurityConstant.USER_ID, userId)
        .setPayload(SecurityConstant.AUTHORITIES, authorities)
        .setKey(key)
        .sign();
        return token;
    }

    public static String createToken(String userId, String[] authorities) {
        byte[] key = "1234567890".getBytes();
        return createToken(key, userId, authorities);
    }

    public static String getUsername(String token) {
        JWT jwt = JWT.of(token);
        return jwt.getPayload(SecurityConstant.USERNAME).toString();
    }

    public static String[] getAuthorities(String token) {
        JWT jwt = JWT.of(token);
        return (String[]) jwt.getPayload(SecurityConstant.AUTHORITIES);
    }

    public static String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public static  boolean verifyToken(String token, byte[] key){
        return JWT.of(token).setKey(key).verify();
    }
}
