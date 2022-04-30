package com.shumu.system.account.entity;

import java.time.LocalDateTime;

import com.shumu.common.base.entity.BaseEntity;
import com.shumu.common.office.excel.annotation.Excel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("系统用户")
public class SysAccount extends BaseEntity {
    @ApiModelProperty("账户标识")
    @Excel(name="账户标识", width = 30)
    private String accountIdentifier;
    @ApiModelProperty("账户凭证")
    @Excel(name="账户凭证", width = 30)
    private String accountCredential;
    @ApiModelProperty("账户类型")
    @Excel(name="账户类型", width = 15)
    private Integer accountType;
    @ApiModelProperty("用户ID")
    @Excel(name="用户ID", width = 30)
    private String userId;
    @ApiModelProperty("上次登录时间")
    @Excel(name="上次登录时间", format = "yyyy-MM-dd hh:mm:ss", width = 30)
    private LocalDateTime lastLoginTime;
    @ApiModelProperty("上次登录IP")
    @Excel(name="上次登录IP", width = 30)
    private String lastLoginIp;
    @ApiModelProperty("上次凭证错误时间")
    @Excel(name="上次凭证错误时间", format = "yyyy-MM-dd hh:mm:ss", width = 30)
    private LocalDateTime lastErrorTime;
    @ApiModelProperty("登录错误次数")
    @Excel(name="登录错误次数", width = 15)
    private Integer loginErrorCount;
    @ApiModelProperty("上次凭证修改时间")
    @Excel(name="上次凭证修改时间", format = "yyyy-MM-dd hh:mm:ss", width = 30)
    private LocalDateTime updateCredentialTime;
}
