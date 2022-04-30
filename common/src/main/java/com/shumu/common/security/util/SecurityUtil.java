package com.shumu.common.security.util;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
* @Description: 
* @Author: Li
* @Date: 2022-02-11
* @LastEditTime: 2022-02-11
* @LastEditors: Li
*/
public class SecurityUtil {
    public static UserDetails login(String username, String password, AuthenticationManager authenticationManager) throws AuthenticationException {
        //使用security框架自带的验证token生成器  也可以自定义。
        UsernamePasswordAuthenticationToken token =new UsernamePasswordAuthenticationToken(username,password );
        Authentication authenticate = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserDetails userInfo =  (UserDetails) authenticate.getPrincipal();
        return userInfo;
    }
    
}
