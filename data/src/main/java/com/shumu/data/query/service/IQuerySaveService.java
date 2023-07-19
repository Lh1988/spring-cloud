package com.shumu.data.query.service;

import com.shumu.common.base.service.BaseService;
import com.shumu.data.query.entity.DataQuerySave;
import com.shumu.data.query.model.QuerySaveModel;
/**
* @description: 
* @author: Li
* @date: 2023-07-11
*/
public interface IQuerySaveService extends BaseService<DataQuerySave> {
    /**
     * 获取查询保存Model
     * @param id
     * @return
     */
    public QuerySaveModel getQuerySaveModel(String id);
    /**
     * 添加查询保存
     * @param model
     */
    public void addQuerySave(QuerySaveModel model);
    /**
     * 更新查询保存
     * @param model
     */
    public void updateQuerySave(QuerySaveModel model);
    /**
     * 删除查询保存
     * @param id
     */
    public void deleteQuerySave(String id);
}
