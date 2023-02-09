package com.shumu.data.database.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shumu.data.database.entity.DataTableInfo;

/**
* @description: 
* @author: Li
* @date: 2023-01-11
*/
public interface TableInfoMapper extends BaseMapper<DataTableInfo> {
  /**
  * 
  * table name
  * @author: Li
  * @date: 2023-01-30
  * @return list
  */
  @Select("SELECT table_name FROM data_table_info")
  public List<String> getTableNames();
}
