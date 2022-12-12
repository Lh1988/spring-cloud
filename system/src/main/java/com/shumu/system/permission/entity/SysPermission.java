package com.shumu.system.permission.entity;

import com.shumu.common.base.entity.BaseEntity;
import com.shumu.common.office.excel.annotation.Excel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
* @description: 
* @author: Li
* @date: 2022-09-23
*/
@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel("系统许可")
public class SysPermission extends BaseEntity{
    @ApiModelProperty("许可名称")
    @Excel(name="许可名称", width = 60)
    private String permissionName;
    @ApiModelProperty("基础路径")
    @Excel(name="基础路径", width = 60)
    private String basePath;
    @ApiModelProperty("可访问方法")
    @Excel(name="可访问方法", width = 60)
    private String accessMethods;
    @ApiModelProperty("描述")
    @Excel(name="描述", width = 60)
    private String description;
}
