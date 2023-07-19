package com.shumu.data.query.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shumu.data.query.entity.DataQuerySave;
import com.shumu.data.query.entity.DataQuerySaveCondition;
import com.shumu.data.query.entity.DataQuerySaveSelect;
import com.shumu.data.query.mapper.QuerySaveConditionMapper;
import com.shumu.data.query.mapper.QuerySaveMapper;
import com.shumu.data.query.mapper.QuerySaveSelectMapper;
import com.shumu.data.query.model.QuerySaveModel;
import com.shumu.data.query.service.IQuerySaveService;
/**
* @description: 
* @author: Li
* @date: 2023-07-11
*/
@Service
public class QuerySaveServiceImpl extends ServiceImpl<QuerySaveMapper, DataQuerySave> implements IQuerySaveService {
    @Autowired
    private QuerySaveSelectMapper querySaveSelectMapper;
    @Autowired
    private QuerySaveConditionMapper querySaveConditionMapper;
    @Autowired
    private QuerySaveMapper querySaveMapper;

    @Override
    public QuerySaveModel getQuerySaveModel(String id) {
        return querySaveMapper.getQuerySaveModel(id);
    }

    @Override
    public void addQuerySave(QuerySaveModel model) {
        DataQuerySave querySave = new DataQuerySave();
        querySave.setQueryName(model.getQueryName());
        querySave.setQueryType(model.getQueryType());
        querySave.setQueryIds(model.getQueryIds());
        boolean success = this.save(querySave);
        if(success){
           String id = querySave.getId();
           List<DataQuerySaveSelect> selectList = model.getSelections();
           if(null!=selectList && !selectList.isEmpty()){
             selectList.forEach((item) -> {
                item.setQueryId(id);
                querySaveSelectMapper.insert(item);
             });
           }
           List<DataQuerySaveCondition> conditionList = model.getConditions();
           if(null!=conditionList && !conditionList.isEmpty()){
            conditionList.forEach((item) -> {
                item.setQueryId(id);
                querySaveConditionMapper.insert(item);
             });
           }
        }
    }

    @Override
    public void updateQuerySave(QuerySaveModel model) {
        DataQuerySave querySave = new DataQuerySave();
        querySave.setQueryName(model.getQueryName());
        querySave.setQueryType(model.getQueryType());
        querySave.setQueryIds(model.getQueryIds());
        boolean success = this.updateById(querySave);
        if(success){
           String id = querySave.getId();
           Map<String,Object> map = new HashMap<String,Object>(16);
           map.put("query_id", id);
           List<DataQuerySaveSelect> selectList = model.getSelections();
           if(null!=selectList && !selectList.isEmpty()){
             querySaveSelectMapper.deleteByMap(map);
             selectList.forEach((item) -> {
                item.setQueryId(id);
                querySaveSelectMapper.insert(item);
             });
           }
           List<DataQuerySaveCondition> conditionList = model.getConditions();
           if(null!=conditionList && !conditionList.isEmpty()){
             querySaveConditionMapper.deleteByMap(map);
             conditionList.forEach((item) -> {
                item.setQueryId(id);
                querySaveConditionMapper.insert(item);
             });
           }
        }
    }

    @Override
    public void deleteQuerySave(String id) {
        Map<String,Object> map = new HashMap<>(8);
        map.put("query_id", id);
        querySaveSelectMapper.deleteByMap(map);
        querySaveConditionMapper.deleteByMap(map);
        querySaveMapper.deleteById(id);
    }
    
}
