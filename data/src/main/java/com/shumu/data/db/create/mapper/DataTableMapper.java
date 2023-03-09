package com.shumu.data.db.create.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
}
