package com.shumu.system.menu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shumu.system.menu.entity.SysMenu;
import com.shumu.system.menu.mapper.SysMenuMapper;
import com.shumu.system.menu.model.SysMenuTree;
import com.shumu.system.menu.service.ISysMenuService;
/**
* @description: 
* @author: Li
* @date: 2022-08-28
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper,SysMenu> implements ISysMenuService{
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Override
    public List<SysMenuTree> getMenuTree() {
        return sysMenuMapper.getMenuTree();
    }
    
}
