package com.shumu.data.db.create.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shumu.data.db.create.entity.DataCreateField;
import com.shumu.data.db.create.entity.DataCreateIndex;
import com.shumu.data.db.create.entity.DataCreateTable;
/**
* @description: mapper
* @author: Li
* @date: 2023-02-09
*/
public interface DataTableMapper extends BaseMapper<DataCreateTable> {
    /**
     * 获取当前数据库名
     * @return
     */
    @Select("SELECT database()")
    public String getCurrentDatabase();
    /**
     * 获取
     * @return
     */
    public List<DataCreateTable> getDbTables();
    /**
     * getDbFields
     * @param table
     * @param database
     * @return
     */
    public List<DataCreateField> getDbFields(@Param("table") String table,@Param("database") String database);
    /**
     * getDbIndexes
     * @param table
     * @param database
     * @return
     */
    public List<DataCreateIndex> getDbIndexes(@Param("table") String table,@Param("database") String database);
}
