package com.shumu.data.db.create.service;

import java.util.List;

import com.shumu.common.base.service.BaseService;
import com.shumu.data.db.create.entity.DataCreateField;
/**
* @description: 
* @author: Li
* @date: 2023-02-09
*/
public interface IDataFieldService extends BaseService<DataCreateField>{
    /**
     * getDbFields
     * @param table
     * @param database
     * @return
     */
    public List<DataCreateField> getDbFields(String table, String database);
}
