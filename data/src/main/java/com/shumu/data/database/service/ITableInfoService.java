package com.shumu.data.database.service;

import java.util.List;

import com.shumu.common.base.service.BaseService;
import com.shumu.data.database.entity.DataTableInfo;

/**
* @description: 
* @author: Li
* @date: 2023-01-12
*/
public interface ITableInfoService extends BaseService<DataTableInfo>{
    /**
     * get table name
     * @return list 
     */
    public List<String> getTableNames();
}
