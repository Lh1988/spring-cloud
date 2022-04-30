package com.shumu.common.security.mapper;

import java.util.List;

import com.shumu.common.security.model.UserModel;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
/**
* @Description: 
* @Author: Li
* @Date: 2022-01-14
* @LastEditTime: 2022-01-14
* @LastEditors: Li
*/
@Mapper
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
    List<String> getPermissions(String userId);
    /**
     * 密码错误时更新账户
     * @param username
     * @return
     */
    void passwordErrorUpdate(String username);
    /**
     * 登录成功后更新账户信息
     * @param id
     * @param ip
     */
    void loginUpdate(@Param("id") String id, @Param("ip") String ip);
}
