package com.shumu.common.security.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.shumu.common.redis.service.IRedisService;
import com.shumu.common.security.exception.ValidateCodeException;
import com.shumu.common.security.handler.LoginFailureHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import cn.hutool.crypto.SecureUtil;

/**
 * @Description:
 * @Author: Li
 * @Date: 2022-02-15
 * @LastEditTime: 2022-02-15
 * @LastEditors: Li
 */
public class ValidateCodeAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private IRedisService redisService;

    public static final String VALIDATE_CODE = "captcha";
    public static final String CODE_KEY = "key";
    public static final String POST = "POST";
    public static final String URL = "/login";

    private String codeParameter = VALIDATE_CODE;
    private String keyParameter = CODE_KEY;

    protected String obtainValidateCode(HttpServletRequest request) {
        return request.getParameter(codeParameter);
    }

    protected String obtainCodeKey(HttpServletRequest request) {
        return request.getParameter(keyParameter);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {         
        if (POST.equals(request.getMethod()) && URL.equals(request.getRequestURI())) {
            try {
                validate(request);
            } catch (ValidateCodeException e) {
                new LoginFailureHandler().onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }


    private void validate(HttpServletRequest request) throws ValidateCodeException {
        // 从客户端接收到的验证码
        String captcha = obtainValidateCode(request);
        String key = obtainCodeKey(request);
        if (captcha == null || "".equals(captcha)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        String lowerCaseCaptcha = captcha.trim().toLowerCase();
        // 从redis获取缓存的验证码
        String realKey = SecureUtil.md5(lowerCaseCaptcha + key);
        String captchaCache = redisService.getString(realKey);
        if (captchaCache == null || !captchaCache.equals(lowerCaseCaptcha)) {
            throw new ValidateCodeException("验证码错误");
        }
        // 校验成功之后，移除验证码缓存
        redisService.delete(realKey);
    }
}
