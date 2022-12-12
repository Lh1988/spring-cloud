package com.shumu.system.permission.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shumu.common.base.controller.BaseController;
import com.shumu.common.base.response.BaseResponse;
import com.shumu.system.permission.entity.SysPermission;
import com.shumu.system.permission.entity.SysPermissionRole;
import com.shumu.system.permission.service.ISysPermissionRoleService;
import com.shumu.system.permission.service.ISysPermissionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
* @description: 
* @author: Li
* @date: 2022-09-23
*/
@RestController
@RequestMapping("/sys/permisssion")
@Api(tags = "权限操作")
public class SysPermissionController extends BaseController<SysPermission,ISysPermissionService>{
    @Autowired
    private ISysPermissionRoleService sysPermissionRoleService;
     
    @ApiOperation("添加角色到权限")
    @PostMapping("/addRole")
    public BaseResponse<?> addRoleToPermission(String permissionId, String roleId) {
        SysPermissionRole sysPermissionRole = new SysPermissionRole();
        sysPermissionRole.setPermissionId(permissionId);
        sysPermissionRole.setRoleId(roleId);
        try{
            sysPermissionRoleService.save(sysPermissionRole);
        } catch (Exception e) {
            return BaseResponse.error("添加成失败");
        }
        return BaseResponse.ok("添加成功");
    }

    @ApiOperation("添加角色列表到权限")
    @PostMapping("/addRoles")
    public BaseResponse<?> addRolesToPermission(String permissionId, List<String> roleIds) {
        if(null==roleIds || roleIds.size()==0 || null==permissionId){
            return BaseResponse.error("添加成失败");
        }
        List<SysPermissionRole> entityList = new ArrayList<>();
        for (String roleId : roleIds) {
            SysPermissionRole sysPermissionRole = new SysPermissionRole();
            sysPermissionRole.setPermissionId(permissionId);
            sysPermissionRole.setRoleId(roleId);
            entityList.add(sysPermissionRole);
        }
        try {
            sysPermissionRoleService.saveBatch(entityList);
        } catch (Exception e) {
            return BaseResponse.error("添加成失败");
        }
        return BaseResponse.ok("添加成功");
    }
    @ApiOperation("移除权限一角色")
    @PostMapping("/removeRole")
    public BaseResponse<?> removeRoleFromPermission(String id) {
        try {
            sysPermissionRoleService.removeById(id);
        } catch (Exception e) {
            return BaseResponse.error("移除失败");
        }
        return BaseResponse.ok("移除成功");
    }
    @ApiOperation("清空权限所有角色")
    @PostMapping("/clearRole")
    public BaseResponse<?> clearRoleFromUser(String userId) {
        Map<String,Object> columnMap = new HashMap<>(8);
        columnMap.put("userId", userId);
        try {
            sysPermissionRoleService.removeByMap(columnMap);
        } catch (Exception e) {
            return BaseResponse.error("移除失败");
        }
        return BaseResponse.ok("移除成功");
    }
}
