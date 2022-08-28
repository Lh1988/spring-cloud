package com.shumu.system.menu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @description: 
* @author: Li
* @date: 2022-08-28
*/
@Data
@EqualsAndHashCode(callSuper=false)
public class SysMenuRole {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String menuId;
    private String roleId;
}
