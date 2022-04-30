package com.shumu.system.user.controller;

import com.shumu.common.base.controller.BaseController;
import com.shumu.system.user.entity.SysUser;
import com.shumu.system.user.service.ISysUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @Description: 
* @Author: Li
* @Date: 2022-01-25
* @LastEditTime: 2022-01-25
* @LastEditors: Li
*/
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController<SysUser,ISysUserService> {
}
