package com.shumu.data.db.create.service;

import java.util.List;

import com.shumu.common.base.service.BaseService;
import com.shumu.data.db.create.entity.DataCreateTable;
/**
* @description: 
* @author: Li
* @date: 2023-02-09
*/
public interface IDataTableService extends BaseService<DataCreateTable>{
    /**
     * 获取
     * @return
     */
    public String getCurrentDatabase();
    /**
     * 获取
     * @return
     */
    public List<DataCreateTable> getDbTables();
}
