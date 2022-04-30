package com.shumu.system.login.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
* @Description: 账号登录
* @Author: Li
* @Date: 2022-01-13
* @LastEditTime: 2022-01-13
* @LastEditors: Li
*/
@ApiModel(value = "账号登录")
@Data
public class LoginModel {
    @ApiModelProperty(value = "账号")
    private String username;
	@ApiModelProperty(value = "密码")
    private String password;
	@ApiModelProperty(value = "验证码")
    private String captcha;
	@ApiModelProperty(value = "验证码key")
    private String checkKey;
}
