package com.shumu.common.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shumu.common.base.response.BaseResponse;
import com.shumu.common.security.exception.ValidateCodeException;
import com.shumu.common.security.service.ICommonUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @Author: Li
 * @Date: 2022-02-16
 * @LastEditTime: 2022-02-16
 * @LastEditors: Li
 */
@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Autowired
    private ICommonUserService commonUserService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        BaseResponse<Object> error = BaseResponse.error();
        if (exception instanceof ValidateCodeException) {
            error.setMessage("验证码错误!");
        } else if (exception instanceof BadCredentialsException) {
            String username = request.getParameter("username");
            commonUserService.passwordErrorUpdate(username);
            error.setMessage("用户名或者密码输入错误,请重新输入!");
        } else if (exception instanceof LockedException) {
            error.setMessage("账户被锁定,请联系管理员!");
        } else if (exception instanceof CredentialsExpiredException) {
            error.setMessage("密码过期,请联系管理员!");
        } else if (exception instanceof AccountExpiredException) {
            error.setMessage("账户过期,请联系管理员!");
        } else if (exception instanceof DisabledException) {
            error.setMessage("账户被禁用,请联系管理员!");
        }
        response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(error));
        out.flush();
        out.close();
        log.info(exception.getMessage());
    }

}
