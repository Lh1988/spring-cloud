package com.shumu.data.db.create.model;

import java.util.List;

import com.shumu.data.db.create.entity.DataCreateField;
import com.shumu.data.db.create.entity.DataCreateIndex;

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
@Schema(title= "数据库表索引")
public class CreateTableModel {
    @Schema(title= "ID")
    private String id;
    @Schema(title= "数据库")
    private String databaseName;
    @Schema(title= "表名")
    private String tableName;
    @Schema(title= "旧表名")
    private String oldName;
    @Schema(title= "表注释")
    private String tableComment;
    @Schema(title= "主键类型")
    private Integer primaryKeyType;
    @Schema(title= "表状态")
    private Integer status;
    @Schema(title= "字段列表")
    private List<DataCreateField> fieldList;
    @Schema(title= "索引列表")
    private List<DataCreateIndex> indexList;
}
