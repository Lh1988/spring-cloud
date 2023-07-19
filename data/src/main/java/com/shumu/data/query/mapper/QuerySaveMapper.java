package com.shumu.data.query.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shumu.data.query.entity.DataQuerySave;
import com.shumu.data.query.model.QuerySaveModel;
/**
* @description: 
* @author: Li
* @date: 2023-07-11
*/
public interface QuerySaveMapper extends BaseMapper<DataQuerySave> {
    /**
     * 获取查询保存信息
     * @param id
     * @return
     */
    public QuerySaveModel getQuerySaveModel(String id);
}
