package com.shumu.data.query.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
* @description: 
* @author: Li
* @date: 2023-06-09
*/
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("data_query_project_target")
public class DataProjectTarget {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String projectId;
    private String targetId;
}
