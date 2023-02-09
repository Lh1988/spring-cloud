package com.shumu.data.database.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shumu.data.database.entity.DataTableIndex;

/**
* @description: 
* @author: Li
* @date: 2023-01-11
*/
public interface TableIndexMapper extends BaseMapper<DataTableIndex>{
    /**
     * get fields
     * @param tableId
     * @return
     */
    @Select("SELECT * FROM data_table_index WHERE table_id = #{tableId}")
    public List<DataTableIndex> getIndexesOfTable(String tableId);
    /**
     * delete
     * @param tableId
     */
    @Update("DELETE FROM data_table_index WHERE table_id = #{tableId}")
    public void removeAllIndexOfTable(String tableId);
}
