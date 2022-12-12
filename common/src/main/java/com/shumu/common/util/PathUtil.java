package com.shumu.common.util;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
/**
* @description: 
* @author: Li
* @date: 2022-12-12
*/
@Slf4j
public class PathUtil {
    /**
     * 获取服务器地址
     *
     * @param request
     * @return
     */
    public static String getBaseUrl(HttpServletRequest request) {
        //1.【兼容】兼容微服务下的 base path-------
        String xGatewayBasePath = request.getHeader("X_GATEWAY_BASE_PATH");
        if(StringUtil.isNotEmpty(xGatewayBasePath)){
            log.info("x_gateway_base_path = "+ xGatewayBasePath);
            return  xGatewayBasePath;
        }
        //2.【兼容】SSL认证之后，request.getScheme()获取不到https的问题
        // https://blog.csdn.net/weixin_34376986/article/details/89767950
        String scheme = request.getHeader("X-Forwarded-Scheme");
        if(StringUtil.isEmpty(scheme)){
            scheme = request.getScheme();
        }

        //3.常规操作
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        //返回 host domain
        String baseDomainPath = null;
        int length = 80;
        if(length == serverPort){
            baseDomainPath = scheme + "://" + serverName  + contextPath ;
        }else{
            baseDomainPath = scheme + "://" + serverName + ":" + serverPort + contextPath ;
        }
        log.info("-----Common getBaseUrl----- : " + baseDomainPath);
        return baseDomainPath;
    }



}
