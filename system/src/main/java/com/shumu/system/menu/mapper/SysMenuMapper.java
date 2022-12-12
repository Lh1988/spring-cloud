package com.shumu.system.menu.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shumu.system.menu.entity.SysMenu;
/**
* @description: 
* @author: Li
* @date: 2022-08-28
*/
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * get
     * @author: Li
     * @date: 2022-08-28
     * @return List<SysMenu> 
     */
    public List<SysMenu> getMenuTree();
}
