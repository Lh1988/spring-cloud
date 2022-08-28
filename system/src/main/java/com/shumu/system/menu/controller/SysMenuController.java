package com.shumu.system.menu.controller;

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
import com.shumu.system.menu.entity.SysMenu;
import com.shumu.system.menu.entity.SysMenuRole;
import com.shumu.system.menu.service.ISysMenuRoleService;
import com.shumu.system.menu.service.ISysMenuService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
* @description: 
* @author: Li
* @date: 2022-08-28
*/
@RestController
@RequestMapping("/sys/menu")
@Api(tags = "菜单操作")
public class SysMenuController extends BaseController<SysMenu,ISysMenuService>{
    @Autowired
    private ISysMenuRoleService sysMenuRoleService;
     
    @ApiOperation("添加角色到菜单")
    @PostMapping("/addRole")
    public BaseResponse<?> addRoleToMenu(String menuId, String roleId) {
        SysMenuRole sysMenuRole = new SysMenuRole();
        sysMenuRole.setMenuId(menuId);
        sysMenuRole.setRoleId(roleId);
        try{
            sysMenuRoleService.save(sysMenuRole);
        } catch (Exception e) {
            return BaseResponse.error("添加成失败");
        }
        return BaseResponse.ok("添加成功");
    }

    @ApiOperation("添加角色列表到菜单")
    @PostMapping("/addRoles")
    public BaseResponse<?> addRolesToMenu(String menuId, List<String> roleIds) {
        if(null==roleIds || roleIds.size()==0 || null==menuId){
            return BaseResponse.error("添加成失败");
        }
        List<SysMenuRole> entityList = new ArrayList<>();
        for (String roleId : roleIds) {
            SysMenuRole sysMenuRole = new SysMenuRole();
            sysMenuRole.setMenuId(menuId);
            sysMenuRole.setRoleId(roleId);
            entityList.add(sysMenuRole);
        }
        try {
            sysMenuRoleService.saveBatch(entityList);
        } catch (Exception e) {
            return BaseResponse.error("添加成失败");
        }
        return BaseResponse.ok("添加成功");
    }
    @ApiOperation("移除菜单一角色")
    @PostMapping("/removeRole")
    public BaseResponse<?> removeRoleFromMenu(String id) {
        try {
            sysMenuRoleService.removeById(id);
        } catch (Exception e) {
            return BaseResponse.error("移除失败");
        }
        return BaseResponse.ok("移除成功");
    }
    @ApiOperation("清空菜单所有角色")
    @PostMapping("/clearRole")
    public BaseResponse<?> clearRoleFromUser(String userId) {
        Map<String,Object> columnMap = new HashMap<>(8);
        columnMap.put("userId", userId);
        try {
            sysMenuRoleService.removeByMap(columnMap);
        } catch (Exception e) {
            return BaseResponse.error("移除失败");
        }
        return BaseResponse.ok("移除成功");
    }
}
