package com.shumu.data.query.model;

import java.util.List;

import com.shumu.data.query.entity.DataQuerySaveCondition;
import com.shumu.data.query.entity.DataQuerySaveSelect;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
* @description: 
* @author: Li
* @date: 2023-07-11
*/
@Data
@EqualsAndHashCode(callSuper=false)
public class QuerySaveModel {
    private String id;
    private String projectId;
    private String queryName;
    private Integer queryType;
    private String queryIds;
    private List<DataQuerySaveSelect> selections;
    private List<DataQuerySaveCondition> conditions;
}
