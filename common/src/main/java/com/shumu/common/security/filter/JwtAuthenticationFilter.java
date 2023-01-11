package com.shumu.common.security.filter;

import java.io.IOException;
import java.io.OutputStream;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shumu.common.base.response.BaseResponse;
import com.shumu.common.redis.service.IRedisService;
import com.shumu.common.security.constant.SecurityConstant;
import com.shumu.common.security.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
/**
 * @author: Li
 * @date: 2022-08-28
 * @description: JWT Authentication Filter
 */
public class JwtAuthenticationFilter extends GenericFilterBean {
    @Autowired
    private IRedisService redisService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        /*--1.获取token ----------------------------------------------*/
        String token = JwtUtil.resolveToken(httpServletRequest);
        /*--2.无token的请求（登录等）继续过滤 --------------------------*/
        if (null == token || "".equals(token)) {
            chain.doFilter(request, response);
            return;
        }
        /*--1.验证token ----------------------------------------------*/
        if (JwtUtil.verifyToken(token)) {            
            SecurityContext securityContext = SecurityContextHolder.getContextHolderStrategy().getContext();
            securityContext.setAuthentication(JwtUtil.getAuthentication(token));
            chain.doFilter(request, response);
            return;
        } else {
            String userId = JwtUtil.getUserId(token);
            if (null!=userId && redisService.hasKey(SecurityConstant.TOKEN_PREFIX + userId)) {
                String newToken = JwtUtil.refreshToken(token);
                redisService.setString(SecurityConstant.TOKEN_PREFIX + userId, newToken);
                redisService.setExpire(SecurityConstant.TOKEN_PREFIX + userId, JwtUtil.EXPIRE_TIME * 2);
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(JwtUtil.getAuthentication(token));
                chain.doFilter(request, response);
                return;
            } else {
                OutputStream os = null;
                try {
                    os = httpServletResponse.getOutputStream();
                    httpServletResponse.setCharacterEncoding("UTF-8");
                    httpServletResponse.setStatus(401);
                    os.write(new ObjectMapper().writeValueAsString(BaseResponse.error("token失效")).getBytes("UTF-8"));
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

}
