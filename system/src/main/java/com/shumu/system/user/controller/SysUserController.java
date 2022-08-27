package com.shumu.system.user.controller;

import com.shumu.common.base.controller.BaseController;
import com.shumu.common.base.response.BaseResponse;
import com.shumu.system.user.entity.SysUser;
import com.shumu.system.user.entity.SysUserRole;
import com.shumu.system.user.service.ISysUserRoleService;
import com.shumu.system.user.service.ISysUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @Description: 
* @Author: Li
* @Date: 2022-01-25
* @LastEditTime: 2022-01-25
* @LastEditors: Li
*/
@Api(tags = "用户操作")
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController<SysUser,ISysUserService> {
    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @ApiOperation("添加角色到用户")
    @PostMapping("/addRole")
    public BaseResponse<?> addRoleToUser(String userId, String roleId) {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(userId);
        sysUserRole.setRoleId(roleId);
        try {
            sysUserRoleService.save(sysUserRole);
        } catch (Exception e) {
            return BaseResponse.error("添加成失败");
        }
        return BaseResponse.ok("添加成功");
    }
    @ApiOperation("添加角色到用户")
    @PostMapping("/addRoles")
    public BaseResponse<?> addRolesToUser(String userId, List<String> roleIds) {
        if(null==roleIds || roleIds.size()==0 || null==userId){
            return BaseResponse.error("添加成失败");
        }
        List<SysUserRole> entityList = new ArrayList<>();
        for (String roleId : roleIds) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleId);
            entityList.add(sysUserRole);
        }
        try {
            sysUserRoleService.saveBatch(entityList);
        } catch (Exception e) {
            return BaseResponse.error("添加成失败");
        }
        return BaseResponse.ok("添加成功");
    }
    @ApiOperation("移除用户一角色")
    @PostMapping("/removeRole")
    public BaseResponse<?> removeRoleFromUser(String id) {
        try {
            sysUserRoleService.removeById(id);
        } catch (Exception e) {
            return BaseResponse.error("移除失败");
        }
        return BaseResponse.ok("移除成功");
    }
    @ApiOperation("清空用户所有角色")
    @PostMapping("/clearRole")
    public BaseResponse<?> clearRoleFromUser(String userId) {
        Map<String,Object> columnMap = new HashMap<>(8);
        columnMap.put("userId", userId);
        try {
            sysUserRoleService.removeByMap(columnMap);
        } catch (Exception e) {
            return BaseResponse.error("移除失败");
        }
        return BaseResponse.ok("移除成功");
    }

}
