package com.shumu.data.db.update.service;

import java.util.List;
import java.util.Map;

import com.shumu.common.base.service.BaseService;
import com.shumu.data.db.update.entity.DataUpdateTable;
/**
* @description: DataUpdateService
* @author: Li
* @date: 2023-02-14
*/
public interface IDataUpdateService extends BaseService<DataUpdateTable> {
      /**
     * 
     * @param table
     * @param fields
     * @param data
     */
    public void insertData(String table,List<String> fields,Map<String,Object> data);
    /**
     * 
     * @param table
     * @param fields
     * @param datas
     */
    public void batchInsertData(String table,List<String> fields,List<Map<String,Object>> datas);
    /**
     * 
     * @param table
     * @param id
     * @param fields
     * @param data
     */
    public void updateDataById(String table,String id,List<String> fields,Map<String,Object> data);
    /**
     * 
     * @param table
     * @param flags
     * @param fields
     * @param data
     */
    public void updateDataByFlag(String table,List<String> flags,List<String> fields,Map<String,Object> data);
    /**
     * 
     * @param table
     * @param primary
     * @param flags
     * @return
     */
    public List<String> getPrimaryKey(String table,String primary,List<Map<String,Object>> flags);
}
