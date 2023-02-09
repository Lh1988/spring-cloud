package com.shumu.data.database.service;

import java.util.List;

import com.shumu.common.base.service.BaseService;
import com.shumu.data.database.entity.DataTableField;

/**
* @description: 
* @author: Li
* @date: 2023-01-12
*/
public interface ITableFieldService extends BaseService<DataTableField>{
    /**
     * get fields
     * @param tableId
     * @return
     */
    public List<DataTableField> getFieldsOfTable(String tableId);
    /**
     * delete
     * @param tableId
     * @return
     */
    public boolean removeAllFieldOfTable(String tableId);
}
