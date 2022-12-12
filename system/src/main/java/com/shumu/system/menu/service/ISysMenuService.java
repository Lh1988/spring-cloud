package com.shumu.system.menu.service;

import java.util.List;

import com.shumu.common.base.service.BaseService;
import com.shumu.system.menu.entity.SysMenu;
/**
* @description: 
* @author: Li
* @date: 2022-08-28
*/
public interface ISysMenuService extends BaseService<SysMenu>{
    /**
    * get tree data
    * @author: Li
    * @date: 2022-11-26
    * @return List<SysMenu>
    */
    public List<SysMenu> getMenuTree();
}
