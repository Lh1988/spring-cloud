package com.shumu.data.query.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
* @description: 
* @author: Li
* @date: 2023-07-11
*/
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("data_query_save_condtion")
public class DataQuerySaveCondition {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String queryId;
    private String targetId;
    private String conditionOperator;
    private String conditionValue;
    private String connectType;
    private Integer orderIndex;
}
