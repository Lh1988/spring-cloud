package com.shumu.data.database.entity;

import java.time.LocalDateTime;

import com.shumu.common.base.entity.BaseEntity;
import com.shumu.common.office.excel.annotation.Excel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
* @description: 
* @author: Li
* @date: 2023-01-10
*/
@Data
@EqualsAndHashCode(callSuper=false)
@Schema(title= "数据库表信息")
public class DataTableInfo extends BaseEntity {
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
    @Schema(title= "表类型")
    @Excel(name="表类型", width = 60, replace = {"1:自定义","2:关联生成","3:表视图"})
    private Integer tableType;
    @Schema(title= "主键类型")
    @Excel(name="主键类型", width = 60, replace = {"0:AUTO","1:NONE","2:INPUT","3:ASSIGN_ID","4:ASSIGN_UUID"})
    private Integer primaryKeyType;
    @Schema(title= "Oracle主键序列")
    @Excel(name="主键序列", width = 60)
    private String primaryKeySequence;
    @Schema(title= "关联SQL")
    @Excel(name="关联SQL", width = 60)
    private String referencedSql;
    @Schema(title= "参数名称")
    @Excel(name="参数名称", width = 60)
    private String paramName;
    @Schema(title= "默认参数值")
    @Excel(name="默认参数值", width = 60)
    private String paramValue;
    @Schema(title= "数据更新时间")
    @Excel(name="数据更新时间", width = 60)
    private LocalDateTime dataUpdateTime;
}
