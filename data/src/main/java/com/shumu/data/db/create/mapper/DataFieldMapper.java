package com.shumu.data.db.create.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shumu.data.db.create.entity.DataCreateField;

/**
* @description: 
* @author: Li
* @date: 2023-02-09
*/
public interface DataFieldMapper extends BaseMapper<DataCreateField>{
    /**
     * getDbFields
     * @param table
     * @param database
     * @return
     */
    public List<DataCreateField> getDbFields(@Param("table") String table,@Param("database") String database);
}
