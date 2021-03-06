package com.shumu.system.user.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shumu.system.user.entity.SysUser;

import org.apache.ibatis.annotations.Select;
/**
* @Description: 
* @Author: Li
* @Date: 2022-01-24
* @LastEditTime: 2022-01-24
* @LastEditors: Li
*/
public interface SysUserMapper extends BaseMapper<SysUser>{
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
    @Select("SELECT * FROM sys_user WHERE name = #{name}")
    List<SysUser> getUserByName(String name);
    /**
     * 通过email获取user
     * @param email
     * @return
     */
    @Select("SELECT * FROM sys_user WHERE email = #{email}")
    SysUser getUserByEmail(String email);
    /**
     * 通过phone获取user
     * @param phone
     * @return
     */
    @Select("SELECT * FROM sys_user WHERE phone = #{phone}")
    SysUser getUserByPhone(String phone);
}
