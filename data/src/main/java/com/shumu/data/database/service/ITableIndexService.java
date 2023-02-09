package com.shumu.data.database.service;

import java.util.List;

import com.shumu.common.base.service.BaseService;
import com.shumu.data.database.entity.DataTableIndex;

/**
* @description: 
* @author: Li
* @date: 2023-01-12
*/
public interface ITableIndexService extends BaseService<DataTableIndex> {
    /**
     * get fields
     * @param tableId
     * @return
     */
    public List<DataTableIndex> getIndexesOfTable(String tableId);
    /**
     * delete
     * @param tableId
     * @return
     */
    public boolean removeAllIndexOfTable(String tableId);
}
