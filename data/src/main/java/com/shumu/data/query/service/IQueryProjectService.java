package com.shumu.data.query.service;

import java.util.List;
import java.util.Map;

import com.shumu.common.base.service.BaseService;
import com.shumu.data.query.entity.DataQueryConnect;
import com.shumu.data.query.entity.DataQueryProject;
import com.shumu.data.query.entity.DataQueryTable;
import com.shumu.data.query.entity.DataQueryTarget;
/**
* @description: 
* @author: Li
* @date: 2023-05-31
*/
public interface IQueryProjectService extends BaseService<DataQueryProject> {
    /**
     * 获取项目的指标
     * @param projectId
     * @return
     */
    public List<DataQueryTarget> getTarget4Project(String projectId);
    /**
     * 获取项目的指标
     * @param projectId
     * @return
     */
    public List<DataQueryTable> getTable4Project(String projectId);
    /**
     * 获取项目的指标
     * @param projectId
     * @return
     */
    public List<DataQueryConnect> getConnect4Project(String projectId);
    /**
     * 查询数据
     * @param sql
     * @return
     */
    public List<Map<String,Object>> queryData(String sql);
}
