package com.shumu.data.query.entity;

import com.shumu.common.base.entity.BaseEntity;
import com.shumu.common.office.excel.annotation.Excel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
* @description: 保存查询
* @author: Li
* @date: 2023-07-11
*/
@Data
@EqualsAndHashCode(callSuper=false)
@Schema(title= "保存查询")
public class DataQuerySave extends BaseEntity {
    @Schema(title= "项目ID")
    @Excel(name="项目ID", width = 60)
    private String projectId;
    @Schema(title= "查询名称")
    @Excel(name="查询名称", width = 60)
    private String queryName;
    @Schema(title= "查询类型")
    @Excel(name="查询类型", width = 60)
    private Integer queryType;
    @Schema(title= "查询ID")
    @Excel(name="查询ID", width = 60)
    private String queryIds;
}
