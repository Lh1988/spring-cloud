package com.shumu.common.security.filter;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
/**
* @Description: 
* @Author: Li
* @Date: 2022-03-25
* @LastEditTime: 2022-03-25
* @LastEditors: Li
*/
public class AccountPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(getPasswordParameter());
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(getUsernameParameter());
    }
    
}
