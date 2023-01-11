package com.shumu.system.user.entity;

import java.time.LocalDateTime;

import com.shumu.common.base.entity.BaseEntity;
import com.shumu.common.office.excel.annotation.Excel;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title= "系统用户")
public class SysUser extends BaseEntity{
    @Schema(title = "用户名",type ="String")
    @Excel(name="用户名", width = 15)
    private String username;

    @Schema(title = "昵称",type ="String")
    @Excel(name="昵称", width = 15)
    private String nickname;

    @Schema(title = "真名",type ="String")
    @Excel(name="真名", width = 15)
    private String realname;

    @Schema(title = "性别",type ="Integer")
    @Excel(name="性别", width = 10, replace = {"1:男","0:女"})
    private Integer sex;

    @Schema(title = "邮箱",type ="String")
    @Excel(name="邮箱", width = 30)
    private String email;

    @Schema(title = "手机号",type ="String")
    @Excel(name="手机号", width = 20)
    private String phone;

    @Schema(title = "头像",type ="String")
    @Excel(name="头像", width = 15)
    private String avatar;

    @Schema(title = "生日",type ="LocalDateTime")
    @Excel(name="生日", width = 20, format = "yyyy-MM-dd")
    private LocalDateTime birthday;

    @Schema(title = "备注信息",type ="String")
    @Excel(name="备注信息", width = 30)
    private String description;
}
