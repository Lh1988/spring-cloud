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
@Schema(title= "数据库表字段")
public class TableField extends BaseEntity {
    @Schema(title= "所属表ID")
    @Excel(name="所属表ID", width = 60)
    private String tableId;
    @Schema(title= "字段名")
    @Excel(name="字段名", width = 60)
    private String fieldName;
    @Schema(title= "旧字段名")
    @Excel(name="旧字段名", width = 60)
    private String fieldNameOld;
    @Schema(title= "字段注释")
    @Excel(name="字段注释", width = 60)
    private String fieldComment;
    @Schema(title= "是否可空")
    @Excel(name="是否可空", width = 60)
    private Boolean isNotNull;
    @Schema(title= "是否唯一")
    @Excel(name="是否唯一", width = 60)
    private Boolean isUnique;
    @Schema(title= "是否主键")
    @Excel(name="是否主键", width = 60)
    private Boolean isPrimaryKey;
    @Schema(title= "字段类型")
    @Excel(name="字段类型", width = 60, replace = {"1:整数","2:浮点数","3:字符串","4:布尔型","5:日期时间","6:日期","7:时间","8:对象","8:文本"})
    private Integer fieldType;
    @Schema(title= "字段长度")
    @Excel(name="字段长度", width = 60)
    private Integer fieldLength;
    @Schema(title= "小数位数")
    @Excel(name="小数位数", width = 60)
    private Integer decimalDigits;
    @Schema(title= "关联表")
    @Excel(name="关联表", width = 60)
    private String referencedTable;
    @Schema(title= "关联字段")
    @Excel(name="关联字段", width = 60)
    private String referencedField;
    @Schema(title= "默认值")
    @Excel(name="默认值", width = 60)
    private String defaultValue;
    @Schema(title= "字段顺序")
    @Excel(name="字段顺序", width = 60)
    private Integer fieldOrder;
}
