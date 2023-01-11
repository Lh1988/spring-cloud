package com.shumu.system.database.model;

import java.time.LocalDateTime;
import java.util.List;

import com.shumu.system.database.entity.TableField;
import com.shumu.system.database.entity.TableIndex;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
/**
* @description: 
* @author: Li
* @date: 2023-01-11
*/
@Data
@Schema(title= "数据库表信息")
public class TableModel {
    @Schema(title= "表名")
    private String tableName;
    @Schema(title= "表注释")
    private String tableComment;
    @Schema(title= "表类型")
    private Integer tableType;
    @Schema(title= "主键")
    private String primaryKey;
    @Schema(title= "主键类型")
    private Integer idType;
    @Schema(title= "Oracle主键序列")
    private String idSequence;
    @Schema(title= "关联的表")
    private String referencedTables;
    @Schema(title= "数据更新时间")
    private LocalDateTime dataUpdateTime;
    @Schema(title= "数据更新时间")
    private List<TableField> fields;
    @Schema(title= "数据更新时间")
    private List<TableIndex> dexines;
}
