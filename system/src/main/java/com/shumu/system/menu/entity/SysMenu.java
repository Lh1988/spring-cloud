package com.shumu.system.menu.entity;

import com.shumu.common.base.entity.BaseEntity;
import com.shumu.common.office.excel.annotation.Excel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
* @description: 
* @author: Li
* @date: 2022-08-28
*/
@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel("系统菜单")
public class SysMenu extends BaseEntity{
    @ApiModelProperty("上级ID")
    @Excel(name="上级ID", width = 60)
    private String pid;
    @ApiModelProperty("菜单类型,0:菜单节点;1:路由;2:链接")
    @Excel(name="菜单类型", width = 30)
    private Integer menuType;
    @ApiModelProperty("菜单标题")
    @Excel(name="菜单标题", width = 30)
    private String title;
    @ApiModelProperty("菜单图标")
    @Excel(name="菜单图标", width = 30)
    private String icon;
    @ApiModelProperty("路由名称")
    @Excel(name="路由名称", width = 30)
    private String name;
    @ApiModelProperty("地址路径")
    @Excel(name="地址路径", width = 60)
    private String url;
    @ApiModelProperty("跳转地址")
    @Excel(name="跳转地址", width = 60)
    private String redirect;
    @ApiModelProperty("路由组件")
    @Excel(name="路由组件", width = 60)
    private String component;
    @ApiModelProperty("组件名称")
    @Excel(name="组件名称", width = 30)
    private String componentName;
    @ApiModelProperty("是否缓存")
    @Excel(name="是否缓存", width = 30)
    private Boolean keepAlive;
    @ApiModelProperty("是否隐藏")
    @Excel(name="是否隐藏", width = 30)
    private Boolean hidden;
    @ApiModelProperty("是否路由")
    @Excel(name="是否路由", width = 30)
    private Boolean route;
    @ApiModelProperty("排列顺序")
    @Excel(name="排列顺序", width = 30)
    private Integer sortIndex;
    @ApiModelProperty("菜单描述")
    @Excel(name="菜单描述", width = 60)
    private String description;
    private SysMenu[] children;
}
