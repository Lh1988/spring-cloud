package com.shumu.data.query.entity;

import com.shumu.common.base.entity.BaseEntity;
import com.shumu.common.office.excel.annotation.Excel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @description: 
* @author: Li
* @date: 2023-05-31
*/
@Data
@EqualsAndHashCode(callSuper=false)
@Schema(title= "查询指标")
public class DataQueryTarget extends BaseEntity {
    @Schema(title= "指标名")
    @Excel(name="指标名", width = 60)
    private String targetName;
    @Schema(title= "所在表")
    @Excel(name="所在表", width = 60)
    private String targetTable;
    @Schema(title= "指标字段")
    @Excel(name="指标字段", width = 60)
    private String targetField;
    @Schema(title= "指标类型")
    @Excel(name="指标类型", width = 60)
    private Integer targetType;
    @Schema(title= "指标取值")
    @Excel(name="指标取值", width = 60)
    private String targetValue;
    @Schema(title= "所在文件夹")
    @Excel(name="所在文件夹", width = 60)
    private String parentFolder;
    @Schema(title= "排序序号")
    @Excel(name="排序序号", width = 60)
    private Integer orderIndex;
}
