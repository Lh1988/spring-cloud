package com.shumu.system.database.entity;

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
@Schema(title= "数据库表索引")
public class TableIndex extends BaseEntity {
    @Schema(title= "所属表ID")
    @Excel(name="所属表ID", width = 60)
    private String tableId;
    @Schema(title= "索引名称")
    @Excel(name="索引名称", width = 60)
    private String indexName;
    @Schema(title= "索引字段")
    @Excel(name="索引字段", width = 60)
    private String indexField;
    @Schema(title= "索引类型")
    @Excel(name="索引类型", width = 60, replace = {"0:普通","1:唯一","2:组合"})
    private String indexType;
}
