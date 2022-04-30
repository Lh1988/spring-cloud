package com.shumu.system.user.entity;

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
* @Date: 2022-01-24
* @LastEditTime: 2022-01-24
* @LastEditors: Li
*/
@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel("系统用户")
public class SysUser extends BaseEntity{
    @ApiModelProperty(value ="用户名",dataType ="String")
    @Excel(name="用户名", width = 15)
    private String username;

    @ApiModelProperty(value ="昵称",dataType ="String")
    @Excel(name="昵称", width = 15)
    private String name;

    @ApiModelProperty(value ="性别",dataType ="Integer")
    @Excel(name="性别", width = 10, replace = {"1:男","0:女"})
    private Integer sex;

    @ApiModelProperty(value ="邮箱",dataType ="String")
    @Excel(name="邮箱", width = 30)
    private String email;

    @ApiModelProperty(value ="手机号",dataType ="String")
    @Excel(name="手机号", width = 20)
    private String phone;

    @ApiModelProperty(value ="头像",dataType ="String")
    @Excel(name="头像", width = 15)
    private String avatar;

    @ApiModelProperty(value ="生日",dataType ="LocalDateTime")
    @Excel(name="生日", width = 20, format = "yyyy-MM-dd")
    private LocalDateTime birthday;

    @ApiModelProperty(value ="备注信息",dataType ="String")
    @Excel(name="备注信息", width = 30)
    private String description;
}
