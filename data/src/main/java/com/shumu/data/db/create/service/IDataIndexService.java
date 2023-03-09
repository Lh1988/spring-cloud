package com.shumu.data.db.create.service;

import java.util.List;

import com.shumu.common.base.service.BaseService;
import com.shumu.data.db.create.entity.DataCreateIndex;

/**
* @description: 
* @author: Li
* @date: 2023-02-09
*/
public interface IDataIndexService extends BaseService<DataCreateIndex>{
    /**
     * getDbIndexes
     * @param table
     * @param database
     * @return
     */
    public List<DataCreateIndex> getDbIndexes(String table,String database);
}
