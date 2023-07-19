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
@Schema(title= "查询值字典")
public class DataQueryDict extends BaseEntity{
    @Schema(title= "字典名")
    @Excel(name="字典名", width = 60)
    private String dictName;
    @Schema(title= "值类型")
    @Excel(name="值类型", width = 60)
    private Integer dictType;
}
