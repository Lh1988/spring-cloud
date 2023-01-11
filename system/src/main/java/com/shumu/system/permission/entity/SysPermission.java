package com.shumu.system.permission.entity;

import com.shumu.common.base.entity.BaseEntity;
import com.shumu.common.office.excel.annotation.Excel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
* @description: 
* @author: Li
* @date: 2022-09-23
*/
@Data
@EqualsAndHashCode(callSuper=false)
@Schema(title= "系统许可")
public class SysPermission extends BaseEntity{
    @Schema(title= "许可名称")
    @Excel(name="许可名称", width = 60)
    private String permissionName;
    @Schema(title= "基础路径")
    @Excel(name="基础路径", width = 60)
    private String basePath;
    @Schema(title= "可访问方法")
    @Excel(name="可访问方法", width = 60)
    private String accessMethods;
    @Schema(title= "描述")
    @Excel(name="描述", width = 60)
    private String description;
}
