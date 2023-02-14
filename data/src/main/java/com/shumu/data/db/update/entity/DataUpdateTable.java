package com.shumu.data.db.update.entity;

import java.time.LocalDateTime;

import com.shumu.common.base.entity.BaseEntity;
import com.shumu.common.office.excel.annotation.Excel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
* @description: DataUpdateTable
* @author: Li
* @date: 2023-02-13
*/
@Data
@EqualsAndHashCode(callSuper=false)
@Schema(title= "表更新参数")
public class DataUpdateTable extends BaseEntity {
    @Schema(title= "对应表ID")
    @Excel(name="对应表ID", width = 60)
    private String tableId;
    @Schema(title= "表别称")
    @Excel(name="表别称", width = 60)
    private String tableTitle;
    @Schema(title= "主键字段")
    @Excel(name="主键字段", width = 60)
    private String primaryKey;
    @Schema(title= "主键生成器")
    @Excel(name="主键生成器", width = 60)
    private Integer primaryKeyGenerator;
    @Schema(title= "更新字段")
    @Excel(name="更新字段", width = 60)
    private String updateField;
    @Schema(title= "更新时间")
    @Excel(name="更新时间", width = 60)
    private LocalDateTime updateTime;
    @Schema(title= "是否SQL关联更新")
    @Excel(name="是否SQL关联更新", width = 60)
    private Boolean useSql;
    @Schema(title= "参数配置")
    @Excel(name="参数配置", width = 60)
    private String paramConfig;
    @Schema(title= "参数值记录")
    @Excel(name="参数值记录", width = 60)
    private String updateParam;
    @Schema(title= "选择字段语句")
    @Excel(name="选择字段语句", width = 60)
    private String selectSql;
    @Schema(title= "选择表格语句")
    @Excel(name="选择表格语句", width = 60)
    private String fromSql;
    @Schema(title= "条件语句")
    @Excel(name="条件语句", width = 60)
    private String whereSql;
    @Schema(title= "额外语句")
    @Excel(name="额外语句", width = 60)
    private String groupSql;
}
