package com.shumu.system.login.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
/**
* @Description: 账号登录
* @Author: Li
* @Date: 2022-01-13
* @LastEditTime: 2022-01-13
* @LastEditors: Li
*/
@Schema(title = "账号登录")
@Data
public class LoginModel {
    @Schema(title = "账号")
    private String account;
	@Schema(title = "密码")
    private String password;
	@Schema(title = "验证码")
    private String captcha;
	@Schema(title = "验证码key")
    private String key;
}
