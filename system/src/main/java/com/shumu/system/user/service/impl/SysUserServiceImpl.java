package com.shumu.system.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shumu.system.menu.entity.SysMenu;
import com.shumu.system.permission.entity.SysPermission;
import com.shumu.system.user.entity.SysUser;
import com.shumu.system.user.mapper.SysUserMapper;
import com.shumu.system.user.service.ISysUserService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
* @Description: 
* @Author: Li
* @Date: 2022-01-25
* @LastEditTime: 2022-01-25
* @LastEditors: Li
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> implements ISysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public SysUser getUserByUsername(String username) {
        return sysUserMapper.getUserByUsername(username);
    }

    @Override
    public SysUser getUserByPhone(String phone) {
        return sysUserMapper.getUserByPhone(phone);
    }

    @Override
    public SysUser getUserByEmail(String email) {
        return sysUserMapper.getUserByEmail(email);
    }

    @Override
    public boolean enableUser(String id) {
        SysUser user = sysUserMapper.selectById(id);
        if(user.getStatus()!=1){
            user.setStatus(1);
            sysUserMapper.updateById(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean disableUser(String id) {
        SysUser user = sysUserMapper.selectById(id);
        if(user.getStatus()!=0){
            user.setStatus(0);
            sysUserMapper.updateById(user);
            return true;
        }
        return false;
    }

    @Override
    public List<SysMenu> getMenusByUserId(String id) {
        return sysUserMapper.getMenusByUserId(id);
    }

    @Override
    public List<SysPermission> getPermissionsByUserId(String id) {
        return sysUserMapper.getPermissionsByUserId(id);
    }
    
}
