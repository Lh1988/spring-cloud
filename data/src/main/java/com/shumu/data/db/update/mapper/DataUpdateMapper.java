package com.shumu.data.db.update.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shumu.data.db.update.entity.DataUpdateTable;
/**
* @description: UpdateMapper
* @author: Li
* @date: 2023-02-14
*/
public interface DataUpdateMapper extends BaseMapper<DataUpdateTable>{
    /**
     * insertData
     * @param table
     * @param fields
     * @param data
     */
    public void insertData(@Param("table") String table,@Param("fields") List<String> fields,@Param("data") Map<String,Object> data);
    /**
     * batchInsertData
     * @param table
     * @param fields
     * @param datas
     */
    public void batchInsertData(@Param("table") String table,@Param("fields") List<String> fields,@Param("datas") List<Map<String,Object>> datas);
    /**
     * updateDataById
     * @param table
     * @param id
     * @param fields
     * @param data
     */
    public void updateDataById(@Param("table") String table,@Param("id") String id,@Param("fields") List<String> fields,@Param("data") Map<String,Object> data);
    /**
     * updateDataByFlag
     * @param table
     * @param flags
     * @param fields
     * @param data
     */
    public void updateDataByFlag(@Param("table") String table,@Param("flags") List<String> flags,@Param("fields") List<String> fields,@Param("data") Map<String,Object> data);
    /**
     * Primary Key
     * @param table
     * @param primary
     * @param flags
     * @return
     */
    public List<String> getPrimaryKey(@Param("tableName") String table,@Param("primaryKey") String primary,@Param("flags") List<Map<String,Object>> flags);
}
