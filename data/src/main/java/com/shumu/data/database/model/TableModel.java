package com.shumu.data.database.model;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.shumu.data.database.entity.DataTableField;
import com.shumu.data.database.entity.DataTableIndex;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
/**
* @description: 
* @author: Li
* @date: 2023-01-11
*/
@Data
@Schema(title= "数据库表信息")
public class TableModel  implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_ID)
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
    @Schema(title= "表类型")
    private Integer tableType;
    @Schema(title= "主键类型")
    private Integer primaryKeyType;
    @Schema(title= "Oracle主键序列")
    private String primaryKeySequence;
    @Schema(title= "关联SQL")
    private String referencedSql;
    @Schema(title= "参数名称")
    private String paramName;
    @Schema(title= "默认参数值")
    private String paramValue;
    @Schema(title= "表状态")
    private Integer status;
    @Schema(title= "字段列表")
    private List<DataTableField> fieldList;
    @Schema(title= "索引列表")
    private List<DataTableIndex> indexList;
}
