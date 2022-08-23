package com.shumu.common.security.service.impl;

import java.util.List;

import com.shumu.common.security.mapper.CommonUserMapper;
import com.shumu.common.security.model.UserModel;
import com.shumu.common.security.service.ICommonUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
* @Description: 
* @Author: Li
* @Date: 2022-01-18
* @LastEditTime: 2022-01-18
* @LastEditors: Li
*/
@Service
public class CommonUserServiceImpl implements ICommonUserService  {
    @Autowired
    private CommonUserMapper commonUserMapper;

    @Override
    public UserModel getUserByName(String username) {
        List<UserModel> list = commonUserMapper.getUserByName(username);
        if(list!=null && !list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<String> getRoles(String userId) {
        return commonUserMapper.getRoles(userId);
    }

    @Override
    public List<String> getPermissions(String userId) {
        return commonUserMapper.getPermissions(userId);
    }

    @Override
    public boolean passwordErrorUpdate(String id) {
        try {
            commonUserMapper.passwordErrorUpdate(id); 
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean loginUpdate(String id, String ip) {
        try {
            commonUserMapper.loginUpdate(id, ip); 
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
}
