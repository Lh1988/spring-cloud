package com.shumu.system.permission.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
* @description: 
* @author: Li
* @date: 2022-09-23
*/
@Data
@EqualsAndHashCode(callSuper=false)
public class SysPermissionRole {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String permissionId;
    private String roleId;
}
