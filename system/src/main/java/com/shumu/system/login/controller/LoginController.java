package com.shumu.system.login.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.shumu.common.base.response.BaseResponse;
import com.shumu.common.redis.service.IRedisService;
import com.shumu.common.security.constant.SecurityConstant;
import com.shumu.common.security.model.UserDetailsModel;
import com.shumu.common.security.service.ICommonUserService;
import com.shumu.common.security.util.JwtUtil;
import com.shumu.common.util.IpUtil;
import com.shumu.system.login.model.LoginModel;
import com.shumu.system.menu.entity.SysMenu;
import com.shumu.system.permission.entity.SysPermission;
import com.shumu.system.user.entity.SysUser;
import com.shumu.system.user.service.ISysUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
    private UserDetailsService userDetailsService;
    @Autowired
    private IRedisService redisService;
    @Autowired
    private ICommonUserService commonUserService;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    /**
     * 
     * @param loginModel
     * @return
     */
    @ApiOperation("登录接口")
	@RequestMapping(value = "/account", method = RequestMethod.POST)
    public BaseResponse<?> login(HttpServletRequest request, @RequestBody LoginModel loginModel){
        /*--1.1.图形验证码验证：获取验证码与验证码缓存key --------------------------*/
        String captcha = loginModel.getCaptcha();
        String key = loginModel.getKey();
        String lowerCaseCaptcha = captcha.trim().toLowerCase();
        /*--1.2.图形验证码验证：从redis获取缓存的验证码 ---------------------------*/
        String realKey = SecureUtil.md5(lowerCaseCaptcha + key);
        String captchaCache = redisService.getString(realKey);
        /*--1.3.图形验证码验证：验证验证码 ---------------------------------------*/
        if (captchaCache == null || !captchaCache.equals(lowerCaseCaptcha)) {
            return BaseResponse.error("验证码错误！");
        }
        /*--2.1.登录账户验证：获取账户（用户） -----------------------------------*/
        String account = loginModel.getAccount();
        UserDetailsModel user = (UserDetailsModel) userDetailsService.loadUserByUsername(account);
        /*--2.2.登录账户验证：验证用户 ------------------------------------------*/
        if(!user.isEnabled()){
            return BaseResponse.error("账户"+account+"未启用！");
        }
        if(!user.isAccountNonExpired()){
            return BaseResponse.error("账户已过期，请联系管理员！");
        }
        if(!user.isAccountNonLocked()){
            return BaseResponse.error("账户已锁定，请联系管理员！");
        }
        if(!user.isCredentialsNonExpired()){
            return BaseResponse.error("密码已锁定，请联系管理员！");
        }
        /*--3.1.登录密码验证：获取前端密码 ------------------------------------*/
		String password = loginModel.getPassword();
        /*--3.2.登录密码验证：用托管密码编码器验证密码--------------------------*/
        if(!passwordEncoder().matches(password, user.getPassword())){
            //密码正确，更新密码错误次数
            commonUserService.passwordErrorUpdate(user.getId());
            return BaseResponse.error("密码错误");
        }
        /*--3.2.登录密码验证：验证通过，存储ip，重置密码错误次数--------------------------*/
        String ip = IpUtil.getIpAdrress(request);
        commonUserService.loginUpdate(user.getId(), ip);  
        /*--4.1.登录成功：创建JWT Token -------------------------------------*/
        String userId = user.getUserId();
        Collection<? extends GrantedAuthority> authorities=user.getAuthorities();
        String token = JwtUtil.createToken(userId, account, authorities.toArray(new String[authorities.size()]));
        /*--4.2.登录成功：缓存 Token ----------------------------------------*/
        redisService.setString(SecurityConstant.TOKEN_PREFIX+userId, token);
        redisService.setExpire(SecurityConstant.TOKEN_PREFIX+userId, JwtUtil.EXPIRE_TIME*2);
        /*--4.3.登录成功：返回响应 ------------------------------------------*/
        redisService.delete(realKey);
        log.info(account+"-登录成功-"+ ip +"-"+LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        return BaseResponse.ok(token, "登录成功！");
    }
    /**
     * 通过token获取用户信息
     * @param token
     * @return
     */
    @ApiOperation("通过token获取用户信息")
	@GetMapping("/user")
    public BaseResponse<SysUser> getUserByToken(HttpServletRequest request) {
        String token = JwtUtil.resolveToken(request);
        BaseResponse<SysUser> result = new BaseResponse<>();
        result.setSuccess(false);
        String userId = JwtUtil.getUserId(token);
        if(null==userId){
            result.setMessage("Token错误!");            
            return result;
        }
        SysUser user = sysUserService.getById(userId);
        if(null==user){
            result.setMessage("用户不存在!");            
            return result;
        }
        result.setSuccess(true);
        result.setResult(user);
        return result;
    }
    /**
     * 通过token获取系统菜单
     * @param token
     * @return
     */
    @ApiOperation("获取菜单")
	@GetMapping("/menus")
    public BaseResponse<List<SysMenu>> getMenusByToken(HttpServletRequest request) {
        String token = JwtUtil.resolveToken(request);
        BaseResponse<List<SysMenu>> result = new BaseResponse<>();
        result.setSuccess(false);
        String userId = JwtUtil.getUserId(token);
        if(null==userId){
            result.setMessage("Token错误!");            
            return result;
        }
        List<SysMenu> menus = sysUserService.getMenusByUserId(userId);
        if(null==menus || menus.size()==0){
            result.setMessage("选项不存在!");            
            return result;
        }
        result.setSuccess(true);
        result.setResult(menus);
        return result;
    }
    @ApiOperation("获取菜单")
	@GetMapping("/permissions")
    public BaseResponse<List<SysPermission>> getPermissionsByToken(HttpServletRequest request) {
        String token = JwtUtil.resolveToken(request);
        BaseResponse<List<SysPermission>> result = new BaseResponse<>();
        result.setSuccess(false);
        String userId = JwtUtil.getUserId(token);
        if(null==userId){
            result.setMessage("Token错误!");            
            return result;
        }
        List<SysPermission> permissions = sysUserService.getPermissionsByUserId(userId);
        if(null==permissions || permissions.size()==0){
            result.setMessage("选项不存在!");            
            return result;
        }
        result.setSuccess(true);
        result.setResult(permissions);
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
