package com.shumu.system.role.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shumu.common.base.controller.BaseController;
import com.shumu.system.role.entity.SysRole;
import com.shumu.system.role.service.ISysRoleService;

import io.swagger.annotations.Api;
/**
* @description: 
* @author: Li
* @date: 2022-08-27
*/
@RestController
@RequestMapping("/sys/role")
@Api(tags = "角色操作")
public class SysRoleController extends BaseController<SysRole,ISysRoleService> {
    
}
