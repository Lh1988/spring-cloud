package com.shumu.system.user.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shumu.system.user.entity.SysUser;
/**
* @Description: 
* @Author: Li
* @Date: 2022-01-25
* @LastEditTime: 2022-01-25
* @LastEditors: Li
*/
public interface ISysUserService extends IService<SysUser> {
    /**
     * 通过username获取user
     * @param username
     * @return
     */
    SysUser getUserByUsername(String username);
    /**
     * 通过name获取user
     * @param name
     * @return
     */
    List<SysUser> getUserByName(String name);
    /**
     * 通过phone获取user
     * @param phone
     * @return
     */
    SysUser getUserByPhone(String phone);
    /**
     * 通过email获取user
     * @param email
     * @return
     */
    SysUser getUserByEmail(String email);
    /**
     * 启用用户
     * @param id
     * @return
     */
    boolean enableUser(String id);
    /**
     * 废弃用户
     * @param id
     * @return
     */
    boolean disableUser(String id);
}
