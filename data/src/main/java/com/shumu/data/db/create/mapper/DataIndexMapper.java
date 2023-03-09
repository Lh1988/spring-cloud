package com.shumu.data.db.create.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shumu.data.db.create.entity.DataCreateIndex;

/**
* @description: 
* @author: Li
* @date: 2023-02-09
*/
public interface DataIndexMapper extends BaseMapper<DataCreateIndex>{
    /**
     * getDbIndexes
     * @param table
     * @param database
     * @return
     */
    public List<DataCreateIndex> getDbIndexes(@Param("table") String table,@Param("database") String database);
}