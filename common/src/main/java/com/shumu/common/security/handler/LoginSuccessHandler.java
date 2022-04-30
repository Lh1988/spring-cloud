package com.shumu.common.security.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shumu.common.security.model.UserDetailsModel;
import com.shumu.common.security.service.ICommonUserService;
import com.shumu.common.util.IpUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.slf4j.Slf4j;
/**
* @Description: 
* @Author: Li
* @Date: 2022-02-16
* @LastEditTime: 2022-02-16
* @LastEditors: Li
*/
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private ICommonUserService commonUserService;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        UserDetailsModel user = (UserDetailsModel) authentication.getPrincipal();
        
        //  
        String ip = IpUtil.getIpAdrress(request);
        commonUserService.loginUpdate(user.getId(), ip);

        log.info("success");
    }
    
}
