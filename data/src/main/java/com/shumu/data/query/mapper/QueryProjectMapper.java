package com.shumu.data.query.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shumu.data.query.entity.DataQueryConnect;
import com.shumu.data.query.entity.DataQueryProject;
import com.shumu.data.query.entity.DataQueryTable;
import com.shumu.data.query.entity.DataQueryTarget;
import com.shumu.data.query.model.QuerySaveModel;
/**
* @description: 
* @author: Li
* @date: 2023-05-31
*/
public interface QueryProjectMapper extends BaseMapper<DataQueryProject> {
    /**
     * 获取项目的指标
     * @param projectId
     * @return
     */
    @Select("SELECT  t.* FROM data_query_target t,data_query_project_target p WHERE t.id = p.target_id AND p.project_id = #{projectId}")
    public List<DataQueryTarget> getTarget4Project(String projectId);
    /**
     * 获取项目的表
     * @param projectId
     * @return
     */
    @Select("SELECT distinct a.* FROM data_query_target t,data_query_project_target p,data_query_table a WHERE t.id = p.target_id AND t.target_table = a.id AND p.project_id = #{projectId}")
    public List<DataQueryTable> getTable4Project(String projectId);
    /**
     * 获取项目的链接
     * @param projectId
     * @return
     */
    @Select("SELECT distinct c.* FROM data_query_target t,data_query_project_target p,data_query_table a,data_query_connect c WHERE t.id = p.target_id AND t.target_table = a.id AND (c.main_table = a.id OR c.connect_table = a.id) AND p.project_id = #{projectId}")
    public List<DataQueryConnect> getConnect4Project(String projectId);
    /**
     * 数据查询
     * @param sql
     * @return
     */
    @Select(" ${sql} ")
    public List<Map<String,Object>> queryData(String sql);
    /**
     * 保存
     * @param model
     */
    public void addQuerySave(QuerySaveModel model);
}
