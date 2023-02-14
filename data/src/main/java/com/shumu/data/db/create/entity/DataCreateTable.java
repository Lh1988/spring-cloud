package com.shumu.data.db.create.entity;

import com.shumu.common.base.entity.BaseEntity;
import com.shumu.common.office.excel.annotation.Excel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
* @description: 
* @author: Li
* @date: 2023-02-09
*/
@Data
@EqualsAndHashCode(callSuper=false)
@Schema(title= "数据库表信息")
public class DataCreateTable extends BaseEntity {
    @Schema(title= "数据库")
    @Excel(name="数据库", width = 60)
    private String databaseName;
    @Schema(title= "表名")
    @Excel(name="表名", width = 60)
    private String tableName;
    @Schema(title= "旧表名")
    @Excel(name="旧表名", width = 60)
    private String oldName;
    @Schema(title= "表注释")
    @Excel(name="表注释", width = 60)
    private String tableComment;
    @Schema(title= "主键类型")
    @Excel(name="主键类型", width = 60, replace = {"0:AUTO","1:NONE","2:INPUT","3:ASSIGN_ID","4:ASSIGN_UUID"})
    private Integer primaryKeyType;
}
