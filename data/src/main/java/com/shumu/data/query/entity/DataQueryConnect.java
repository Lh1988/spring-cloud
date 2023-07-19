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
@Schema(title= "查询链接")
public class DataQueryConnect extends BaseEntity {
    @Schema(title= "主表")
    @Excel(name="主表", width = 60)
    private String mainTable;
    @Schema(title= "链接表")
    @Excel(name="链接表", width = 60)
    private String connectTable;
    @Schema(title= "链接SQL")
    @Excel(name="链接SQL", width = 60)
    private String connectSql;
}
