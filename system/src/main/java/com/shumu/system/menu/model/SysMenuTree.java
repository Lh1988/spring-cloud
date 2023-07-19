package com.shumu.system.menu.model;


import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
* @description: 
* @author: Li
* @date: 2023-06-01
*/
@Data
@EqualsAndHashCode(callSuper=false)
@Schema(title= "系统菜单树")
public class SysMenuTree {
    @Schema(title="主键",type="String")
    private String id;
    @Schema(title= "上级ID")
    private String pid;
    @Schema(title= "菜单类型,0:一级菜单;1:子菜单;2:链接")
    private Integer menuType;
    @Schema(title= "菜单标题")
    private String title;
    @Schema(title= "菜单图标")
    private String icon;
    @Schema(title= "路由名称")
    private String name;
    @Schema(title= "地址路径")
    private String url;
    @Schema(title= "跳转地址")
    private String redirect;
    @Schema(title= "路由组件")
    private String component;
    @Schema(title= "组件名称")
    private String componentName;
    @Schema(title= "是否缓存")
    private Boolean keepAlive;
    @Schema(title= "是否隐藏")
    private Boolean hidden;
    @Schema(title= "是否路由")
    private Boolean route;
    @Schema(title= "排列顺序")
    private Integer sortIndex;
    @Schema(title= "菜单描述")
    private String description;
    @Schema(title ="状态",type ="Integer")
    private Integer status;
    @Schema(title ="创建者",type ="String")
    private String createBy;
    @Schema(title ="更新者",type ="String")
    private String updateBy;
    @Schema(title ="创建时间",type ="Date")
    private LocalDateTime createTime;
    @Schema(title ="更新时间",type ="Date")
    private LocalDateTime updateTime;
    @Schema(title= "子菜单")
    private SysMenuTree[] children;
}
