package com.shumu.common.security.util;


import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.shumu.common.security.authority.CommonGrantedAuthority;
import com.shumu.common.security.constant.SecurityConstant;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
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

    private static final byte[] KEY = "1234567890".getBytes();
    private static final String BEARER ="Bearer ";

    public static String createToken(byte[] key, String userId, String username, String[] authorities) {
        String token = JWT.create()
        .setNotBefore(DateUtil.offsetSecond(DateUtil.date(), (int) (EXPIRE_TIME/1000)))
        .setPayload(SecurityConstant.USER_ID, userId)
        .setPayload(SecurityConstant.USERNAME, username)
        .setPayload(SecurityConstant.AUTHORITIES, authorities)
        .setKey(key)
        .sign();
        return token;
    }

    public static String refreshToken(String token, byte[] key) {
        JWT jwt = JWT.of(token);
        String userId=jwt.getPayload(SecurityConstant.USER_ID).toString();
        String username=jwt.getPayload(SecurityConstant.USERNAME).toString();
        String[] authorities=(String[]) jwt.getPayload(SecurityConstant.AUTHORITIES);
        return createToken(key, userId, username, authorities);
    }

    public static String refreshToken(String token) {
        return refreshToken(token, KEY);
    }

    public static String createToken(String userId, String username, String[] authorities) {
        return createToken(KEY, userId, username, authorities);
    }

    public static String getUserId(String token) {
        JWT jwt = JWT.of(token);
        String id = "";
        Object object = jwt.getPayload(SecurityConstant.USER_ID);
        if (object != null) {
            id = object.toString();
        }
        return id;
    }

    public static String getUsername(String token) {
        JWT jwt = JWT.of(token);
        return jwt.getPayload(SecurityConstant.USERNAME).toString();
    }


    public static String[] getAuthorities(String token) {
        JWT jwt = JWT.of(token);
        JSONArray arr = (JSONArray)jwt.getPayload(SecurityConstant.AUTHORITIES);
        if(!arr.isEmpty()){
            return arr.toArray(new String[0]);
        }
        return new String[0];
    }


    public static  boolean verifyToken(String token, byte[] key){
        return JWT.of(token).setKey(key).verify();
    }

    public static  boolean verifyToken(String token){
        return JWT.of(token).setKey(KEY).verify();
    }

    public static String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(SecurityConstant.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
        String[] authorities = getAuthorities(token);
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        for(String authority:authorities){
            list.add(new CommonGrantedAuthority(authority));
        }
        return new UsernamePasswordAuthenticationToken(getUserId(token),null,list);
    }
}
