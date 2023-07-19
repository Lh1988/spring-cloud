package com.shumu.system.menu.entity;

import com.shumu.common.base.entity.BaseEntity;
import com.shumu.common.office.excel.annotation.Excel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
* @description: 
* @author: Li
* @date: 2022-08-28
*/
@Data
@EqualsAndHashCode(callSuper=false)
@Schema(title= "系统菜单")
public class SysMenu extends BaseEntity{
    @Schema(title= "上级ID")
    @Excel(name="上级ID", width = 60)
    private String pid;
    @Schema(title= "菜单类型,0:一级菜单;1:子菜单;2:链接")
    @Excel(name="菜单类型", width = 30)
    private Integer menuType;
    @Schema(title= "菜单标题")
    @Excel(name="菜单标题", width = 30)
    private String title;
    @Schema(title= "菜单图标")
    @Excel(name="菜单图标", width = 30)
    private String icon;
    @Schema(title= "路由名称")
    @Excel(name="路由名称", width = 30)
    private String name;
    @Schema(title= "地址路径")
    @Excel(name="地址路径", width = 60)
    private String url;
    @Schema(title= "跳转地址")
    @Excel(name="跳转地址", width = 60)
    private String redirect;
    @Schema(title= "路由组件")
    @Excel(name="路由组件", width = 60)
    private String component;
    @Schema(title= "组件名称")
    @Excel(name="组件名称", width = 30)
    private String componentName;
    @Schema(title= "是否缓存")
    @Excel(name="是否缓存", width = 30)
    private Boolean keepAlive;
    @Schema(title= "是否隐藏")
    @Excel(name="是否隐藏", width = 30)
    private Boolean hidden;
    @Schema(title= "是否路由")
    @Excel(name="是否路由", width = 30)
    private Boolean route;
    @Schema(title= "排列顺序")
    @Excel(name="排列顺序", width = 30)
    private Integer sortIndex;
    @Schema(title= "菜单描述")
    @Excel(name="菜单描述", width = 60)
    private String description;
}
