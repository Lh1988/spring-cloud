package com.shumu.system.account.entity;

import java.time.LocalDateTime;

import com.shumu.common.base.entity.BaseEntity;
import com.shumu.common.office.excel.annotation.Excel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
* @Description: 
* @Author: Li
* @Date: 2022-02-08
* @LastEditTime: 2022-02-08
* @LastEditors: Li
*/
@Data
@EqualsAndHashCode(callSuper=false)
@Schema(title= "系统用户")
public class SysAccount extends BaseEntity {
    @Schema(title= "账户标识")
    @Excel(name="账户标识", width = 30)
    private String accountIdentifier;
    @Schema(title= "账户凭证")
    @Excel(name="账户凭证", width = 30)
    private String accountCredential;
    @Schema(title= "账户类型")
    @Excel(name="账户类型", width = 15)
    private Integer accountType;
    @Schema(title= "用户ID")
    @Excel(name="用户ID", width = 30)
    private String userId;
    @Schema(title= "上次登录时间")
    @Excel(name="上次登录时间", format = "yyyy-MM-dd hh:mm:ss", width = 30)
    private LocalDateTime lastLoginTime;
    @Schema(title= "上次登录IP")
    @Excel(name="上次登录IP", width = 30)
    private String lastLoginIp;
    @Schema(title= "上次凭证错误时间")
    @Excel(name="上次凭证错误时间", format = "yyyy-MM-dd hh:mm:ss", width = 30)
    private LocalDateTime lastErrorTime;
    @Schema(title= "登录错误次数")
    @Excel(name="登录错误次数", width = 15)
    private Integer loginErrorCount;
    @Schema(title= "上次凭证修改时间")
    @Excel(name="上次凭证修改时间", format = "yyyy-MM-dd hh:mm:ss", width = 30)
    private LocalDateTime updateCredentialTime;
}
