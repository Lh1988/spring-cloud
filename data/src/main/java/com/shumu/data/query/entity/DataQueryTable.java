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
@Schema(title= "查询表格")
public class DataQueryTable extends BaseEntity {
    @Schema(title= "数据库")
    @Excel(name="数据库", width = 60)
    private String databaseName;
    @Schema(title= "表名")
    @Excel(name="表名", width = 60)
    private String tableName;
    @Schema(title= "表别名")
    @Excel(name="表别名", width = 60)
    private String tableAlias;
    @Schema(title= "表描述")
    @Excel(name="表描述", width = 60)
    private String tableDesc;
    @Schema(title= "表类型")
    @Excel(name="表类型", width = 60, replace = {"0: 主表","1:附表"})
    private Integer tableType;
    @Schema(title= "表条件")
    @Excel(name="表条件", width = 60)
    private String tableCondition;
}
