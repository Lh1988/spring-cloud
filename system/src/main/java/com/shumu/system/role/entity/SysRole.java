package com.shumu.system.role.entity;

import com.shumu.common.base.entity.BaseEntity;
import com.shumu.common.office.excel.annotation.Excel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
* @Description: 
* @Author: Li
* @Date: 2022-08-26
* @LastEditTime: 2022-08-26
* @LastEditors: Li
*/
@Data
@EqualsAndHashCode(callSuper=false)
@Schema(title= "系统角色")
public class SysRole extends BaseEntity {
    @Schema(title= "角色名称")
    @Excel(name="角色名称", width = 30)
    private String roleName;
    @Schema(title= "角色代码")
    @Excel(name="角色代码", width = 30)
    private String roleCode;
    @Schema(title= "角色描述")
    @Excel(name="角色描述", width = 30)
    private String description;
}
