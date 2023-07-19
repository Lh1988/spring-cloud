package com.shumu.data.query.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shumu.data.query.entity.DataQueryConnect;
import com.shumu.data.query.entity.DataQueryProject;
import com.shumu.data.query.entity.DataQueryTable;
import com.shumu.data.query.entity.DataQueryTarget;
import com.shumu.data.query.mapper.QueryProjectMapper;
import com.shumu.data.query.service.IQueryProjectService;

/**
* @description: 
* @author: Li
* @date: 2023-05-31
*/
@Service
public class QueryProjectServiceImpl extends ServiceImpl<QueryProjectMapper, DataQueryProject> implements IQueryProjectService {
    @Autowired
    private QueryProjectMapper projectMapper;
    @Override
    public List<DataQueryTarget> getTarget4Project(String projectId) {
        return projectMapper.getTarget4Project(projectId);
    }
    @Override
    public List<DataQueryTable> getTable4Project(String projectId) {
        return projectMapper.getTable4Project(projectId);
    }
    @Override
    public List<DataQueryConnect> getConnect4Project(String projectId) {
        return projectMapper.getConnect4Project(projectId);
    }
    @Override
    public List<Map<String, Object>> queryData(String sql) {
        return projectMapper.queryData(sql);
    }
    
}
