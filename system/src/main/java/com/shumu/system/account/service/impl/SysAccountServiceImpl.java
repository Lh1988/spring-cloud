package com.shumu.system.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shumu.system.account.entity.SysAccount;
import com.shumu.system.account.mapper.SysAccountMapper;
import com.shumu.system.account.service.ISysAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
* @Description: 
* @Author: Li
* @Date: 2022-02-10
* @LastEditTime: 2022-02-10
* @LastEditors: Li
*/
@Service
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccount> implements ISysAccountService {
    @Autowired
    private SysAccountMapper sysAccountMapper;
}
