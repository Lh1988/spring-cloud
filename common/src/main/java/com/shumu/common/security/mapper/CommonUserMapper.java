package com.shumu.common.security.mapper;

import java.util.List;
import java.util.Map;

import com.shumu.common.security.model.UserModel;

import org.apache.ibatis.annotations.Param;
/**
* @Description: 
* @Author: Li
* @Date: 2022-01-14
* @LastEditTime: 2022-01-14
* @LastEditors: Li
*/
public interface CommonUserMapper {
    /**
     * 通过用户名获取用户信息
     * @param username 用户名
     * @return
     */
    List<UserModel> getUserByName(@Param("username") String username);
    /**
     * 获取用户对应的角色
     * @param userId 用户ID
     * @return
     */
    List<String> getRoles(String userId);
    /**
     * 获取用户角色对应的权限
     * @param userId
     * @return
     */
    List<Map<String,String>> getPermissions(String userId);
    /**
     * 密码错误时更新账户
     * @param id
     * @return
     */
    void passwordErrorUpdate(String id);
    /**
     * 登录成功后更新账户信息
     * @param id
     * @param ip
     */
    void loginUpdate(@Param("id") String id, @Param("ip") String ip);
}
