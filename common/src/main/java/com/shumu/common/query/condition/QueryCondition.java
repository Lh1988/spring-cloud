package com.shumu.common.query.condition;

import java.io.Serializable;

import lombok.Data;
/**
* @Description: Query Condition
* @Author: Li
* @Date: 2022-01-04
* @LastEditTime: 2022-01-04
* @LastEditors: Li
*/
@Data
public class QueryCondition implements Serializable{
    private String field;
    private String type;
    private String rule;
    private String value;
    private String operator = "AND";
}
