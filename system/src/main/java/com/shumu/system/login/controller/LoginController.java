package com.shumu.system.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.shumu.common.base.response.BaseResponse;
import com.shumu.common.redis.service.IRedisService;
import com.shumu.common.security.constant.SecurityConstant;
import com.shumu.common.security.model.UserDetailsModel;

import com.shumu.common.security.util.JwtUtil;
import com.shumu.system.user.entity.SysUser;
import com.shumu.system.user.service.ISysUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.crypto.SecureUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @Author: Li
 * @Date: 2022-01-13
 * @LastEditTime: 2022-01-13
 * @LastEditors: Li
 */
@RestController
@RequestMapping("/login")
@Api(tags = "用户登录")
@Slf4j
public class LoginController {
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private IRedisService redisService;
    /**
     * 
     * @param loginModel
     * @return
     * @throws Exception
     */
    @ApiOperation("表单登录")
    @RequestMapping(value = "/loginSuccess", method = RequestMethod.GET)
    public BaseResponse<JSONObject> loginSuccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 从session获取认证信息
        HttpSession httpSession = request.getSession();
        SecurityContext context = (SecurityContext) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        UserDetailsModel userDetails = (UserDetailsModel) context.getAuthentication().getPrincipal();
        // 获取账户对应user id
        String userId = userDetails.getUserId();
        // 创建JWT Token
        String token = JwtUtil.createToken(userId, userDetails.getAuthorities().toArray(new String[0]));
        // 缓存 Token
        redisService.setString(SecurityConstant.TOKEN_PREFIX+userId, token);
        redisService.setExpire(SecurityConstant.TOKEN_PREFIX+userId, JwtUtil.EXPIRE_TIME*2);
        // 获取用户信息
        SysUser user = sysUserService.getById(userId);
        
        BaseResponse<JSONObject> result = new BaseResponse<>();
        result.setCode(HttpServletResponse.SC_ACCEPTED);
        result.setSuccess(true);
        result.setMessage("登录成功！");
        JSONObject obj = new JSONObject();
        obj.put("token", token);
        obj.put("user", user);
        result.setResult(obj);
        log.info(userDetails.getUserId());
        return result;
    }

    /**
	 * 后台生成图形验证码 ：有效
	 * @param response
	 * @param key
	 */
	@ApiOperation("获取验证码")
	@GetMapping(value = "/code/{key}")
	public BaseResponse<String> code(HttpServletResponse response,@PathVariable String key){
		BaseResponse<String> res = new BaseResponse<String>();
		try {
			LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(105, 35, 4, 5);
            String code = lineCaptcha.getCode();
            String lowerCaseCode = code.toLowerCase();
            String realKey=SecureUtil.md5(lowerCaseCode+key);
            redisService.setString(realKey, lowerCaseCode, 60);
            //生成验证码图片
            String base64 = lineCaptcha.getImageBase64Data();
			res.setSuccess(true);
			res.setResult(base64);
		} catch (Exception e) {
            res.setSuccess(false);
            res.setMessage("获取验证码出错"+e.getMessage());
			e.printStackTrace();
		}
		return res;
	}

}
