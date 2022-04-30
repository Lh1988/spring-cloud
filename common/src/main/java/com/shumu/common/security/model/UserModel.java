package com.shumu.common.security.model;

import java.time.LocalDateTime;

import lombok.Data;
/**
* @Description: 登录用户
* @Author: Li
* @Date: 2022-01-14
* @LastEditTime: 2022-01-14
* @LastEditors: Li
*/
@Data
public class UserModel {
    private String id;
    private String username;
    private String password;
    private String userId;
    private Integer status;
    private LocalDateTime lastLoginTime;
    private LocalDateTime updateCredentialTime;
    private LocalDateTime lastErrorTime;
    private Integer loginErrorCount;
}
