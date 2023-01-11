package com.shumu.system.user.controller;

import com.shumu.common.base.controller.BaseController;
import com.shumu.common.base.response.BaseResponse;
import com.shumu.system.user.entity.SysUser;
import com.shumu.system.user.entity.SysUserRole;
import com.shumu.system.user.service.ISysUserRoleService;
import com.shumu.system.user.service.ISysUserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
* @Description: 
* @Author: Li
* @Date: 2022-01-25
* @LastEditTime: 2022-01-25
* @LastEditors: Li
*/
@Slf4j
@Tag(name ="用户操作")
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController<SysUser,ISysUserService> {
    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Operation(summary = "添加角色到用户")
    @PostMapping("/addRole")
    public BaseResponse<?> addRoleToUser(@RequestBody Map<String,String> param) {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(param.get("userId"));
        sysUserRole.setRoleId(param.get("roleId"));
        try {
            sysUserRoleService.save(sysUserRole);
        } catch (Exception e) {
            return BaseResponse.error("添加成失败");
        }
        return BaseResponse.ok("添加成功");
    }
    @Operation(summary = "添加角色到用户")
    @PostMapping("/addRoles")
    public BaseResponse<?> addRolesToUser(@RequestBody Map<String,String> param) {
        if(null==param || null==param.get("roleIds") || null==param.get("userId")){
            return BaseResponse.error("添加成失败");
        }
        List<SysUserRole> entityList = new ArrayList<>();
        String[] roleIds = param.get("roleIds").split(",", 0);
        String userId = param.get("userId");
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
    @Operation(summary = "移除用户角色")
    @DeleteMapping("/removeRole")
    public BaseResponse<?> removeRoleFromUser(@RequestParam("userId") String userId, @RequestParam("roleId") String roleId) {
        Map<String,Object> columnMap = new HashMap<>(8);
        columnMap.put("user_id", userId);
        columnMap.put("role_id", roleId);
        log.info(roleId,userId);
        try {
            sysUserRoleService.removeByMap(columnMap);
        } catch (Exception e) {
            return BaseResponse.error("移除失败");
        }
        return BaseResponse.ok("移除成功");
    }

    @Operation(summary = "移除用户一角色")
    @DeleteMapping("/removeRoleById")
    public BaseResponse<?> removeRoleById(String id) {
        try {
            sysUserRoleService.removeById(id);
        } catch (Exception e) {
            return BaseResponse.error("移除失败");
        }
        return BaseResponse.ok("移除成功");
    }
    @Operation(summary = "清空用户所有角色")
    @DeleteMapping("/clearRole")
    public BaseResponse<?> clearRoleFromUser(String userId) {
        Map<String,Object> columnMap = new HashMap<>(8);
        columnMap.put("user_id", userId);
        try {
            sysUserRoleService.removeByMap(columnMap);
        } catch (Exception e) {
            return BaseResponse.error("移除失败");
        }
        return BaseResponse.ok("移除成功");
    }
    @Operation(summary = "用户角色")
    @GetMapping("/getRole")
    public BaseResponse<?> getMenuRole(String userId) {
        List<SysUserRole> list = new ArrayList<>();
        Map<String,Object> columnMap = new HashMap<>(8);
        columnMap.put("user_id", userId);
        try{
            list = sysUserRoleService.listByMap(columnMap);
        } catch (Exception e) {
            return BaseResponse.error("数据获取失败");
        }
        BaseResponse<List<SysUserRole>> result = new BaseResponse<>();
        result.setSuccess(true);
        result.setMessage("数据查询成功!");
        result.setResult(list);
        return result;
    }

}
