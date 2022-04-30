package com.shumu.common.security.service;

import java.util.List;

import com.shumu.common.security.model.UserModel;

/**
 * @Description:
 * @Author: Li
 * @Date: 2022-01-18
 * @LastEditTime: 2022-01-18
 * @LastEditors: Li
 */
public interface ICommonUserService {
    /**
     * 通过用户名获取用户
     * 
     * @param username
     * @return
     */
    UserModel getUserByName(String username);

    /**
     * 获取用户对应的角色
     * 
     * @param userId 用户ID
     * @return
     */
    List<String> getRoles(String userId);

    /**
     * 获取用户角色对应的权限
     * 
     * @param userId
     * @return
     */
    List<String> getPermissions(String userId);
    /**
     * 密码错误时更新账户
     * @param username
     * @return
     */
    boolean passwordErrorUpdate(String username);     
    /**
     * 登录成功后更新账户信息
     * @param id
     * @param ip
     * @return
     */
    boolean loginUpdate(String id, String ip);
}
