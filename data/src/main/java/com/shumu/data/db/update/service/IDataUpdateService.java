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
     * 插入数据
     * @param table
     * @param fields
     * @param data
     */
    public void insertData(String table,List<String> fields,Map<String,Object> data);
    /**
     * 批量插入数据
     * @param table
     * @param fields
     * @param datas
     */
    public void batchInsertData(String table,List<String> fields,List<Map<String,Object>> datas);
    /**
     * 更新数据
     * @param table
     * @param id
     * @param fields
     * @param data
     */
    public void updateDataById(String table,String id,List<String> fields,Map<String,Object> data);
    /**
     * 更新数据
     * @param table
     * @param flags
     * @param fields
     * @param data
     */
    public void updateDataByFlag(String table,List<String> flags,List<String> fields,Map<String,Object> data);
    /**
     * 获取主键
     * @param table
     * @param primary
     * @param flags
     * @return
     */
    public List<String> getPrimaryKey(String table,String primary,List<Map<String,Object>> flags);
}
