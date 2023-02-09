package com.shumu.data.database.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shumu.data.database.entity.DataTableField;

/**
* @description: 
* @author: Li
* @date: 2023-01-11
*/
public interface TableFieldMapper extends BaseMapper<DataTableField>{
    /**
     * get fields
     * @param tableId
     * @return
     */
    @Select("SELECT * FROM data_table_field WHERE table_id = #{tableId} ORDER BY field_order")
    public List<DataTableField> getFieldsOfTable(@Param("tableId") String tableId);
    /**
     * delete
     * @param tableId
     */
    @Update("DELETE FROM data_table_field WHERE table_id = #{tableId}")
    public void removeAllFieldOfTable(@Param("tableId") String tableId);
}
