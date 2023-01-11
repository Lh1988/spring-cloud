package com.shumu.system.database.entity;

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
public class TableInfo extends BaseEntity {
    @Schema(title= "模式名")
    @Excel(name="模式名", width = 60)
    private String schemaName;
    @Schema(title= "表名")
    @Excel(name="表名", width = 60)
    private String tableName;
    @Schema(title= "表注释")
    @Excel(name="表注释", width = 60)
    private String tableComment;
    @Schema(title= "表类型")
    @Excel(name="表类型", width = 60, replace = {"1:自定义","2:关联生成","3:视图"})
    private Integer tableType;
    @Schema(title= "主键")
    @Excel(name="主键", width = 60)
    private String primaryKey;
    @Schema(title= "主键类型")
    @Excel(name="主键类型", width = 60, replace = {"0:AUTO","1:NONE","2:INPUT","3:ASSIGN_ID","4:ASSIGN_UUID"})
    private Integer idType;
    @Schema(title= "Oracle主键序列")
    @Excel(name="主键序列", width = 60)
    private String idSequence;
    @Schema(title= "关联的表")
    @Excel(name="关联的表", width = 60)
    private String referencedTables;
    @Schema(title= "数据更新时间")
    @Excel(name="数据更新时间", width = 60)
    private LocalDateTime dataUpdateTime;
}
