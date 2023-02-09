package com.shumu.system.menu.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.shumu.common.base.controller.BaseController;
import com.shumu.common.base.response.BaseResponse;
import com.shumu.system.menu.entity.SysMenu;
import com.shumu.system.menu.entity.SysMenuRole;
import com.shumu.system.menu.service.ISysMenuRoleService;
import com.shumu.system.menu.service.ISysMenuService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
/**
* @description: 
* @author: Li
* @date: 2022-08-28
*/
@RestController
@RequestMapping("/sys/menu")
@Tag(name ="菜单操作")
public class SysMenuController extends BaseController<SysMenu,ISysMenuService>{
    @Autowired
    private ISysMenuRoleService sysMenuRoleService;
    @Autowired
    private ISysMenuService sysMenuService;

    @Operation(summary = "菜单Tree")
    @GetMapping("/tree")
    public BaseResponse<?> getMenuTree() {
        List<SysMenu> list = new ArrayList<>();
        try{
            list = sysMenuService.getMenuTree();
        } catch (Exception e) {
            return BaseResponse.error("数据获取失败");
        }
        BaseResponse<List<SysMenu>> result = new BaseResponse<>();
        result.setSuccess(true);
        result.setMessage("数据查询成功!");
        result.setResult(list);
        return result;
    }

    @Operation(summary = "菜单角色")
    @GetMapping("/getRole")
    public BaseResponse<?> getMenuRole(String menuId) {
        List<SysMenuRole> list = new ArrayList<>();
        Map<String,Object> columnMap = new HashMap<>(8);
        columnMap.put("menu_id", menuId);
        try{
            list = sysMenuRoleService.listByMap(columnMap);
        } catch (Exception e) {
            return BaseResponse.error("数据获取失败");
        }
        BaseResponse<List<SysMenuRole>> result = new BaseResponse<>();
        result.setSuccess(true);
        result.setMessage("数据查询成功!");
        result.setResult(list);
        return result;
    }
     
    @Operation(summary = "添加角色到菜单")
    @PostMapping("/addRole")
    public BaseResponse<?> addRoleToMenu(@RequestBody SysMenuRole sysMenuRole) {
        try{
            sysMenuRoleService.save(sysMenuRole);
        } catch (Exception e) {
            return BaseResponse.error("添加成失败");
        }
        return BaseResponse.ok("添加成功");
     }

    @Operation(summary = "移除菜单一角色")
    @DeleteMapping("/removeRole")
    public BaseResponse<?> removeRoleFromMenu(String menuId,String roleId) {
        Map<String,Object> columnMap = new HashMap<>(8);
        columnMap.put("menuId", menuId);
        columnMap.put("roleId", roleId);
        try {
            sysMenuRoleService.removeByMap(columnMap);
        } catch (Exception e) {
            return BaseResponse.error("移除失败");
        }
        return BaseResponse.ok("移除成功");
    }
    @Operation(summary = "清空菜单所有角色")
    @DeleteMapping("/clearRole")
    public BaseResponse<?> clearRoleFromUser(String menuId) {
        Map<String,Object> columnMap = new HashMap<>(8);
        columnMap.put("menuId", menuId);
        try {
            sysMenuRoleService.removeByMap(columnMap);
        } catch (Exception e) {
            return BaseResponse.error("移除失败");
        }
        return BaseResponse.ok("移除成功");
    }
}
