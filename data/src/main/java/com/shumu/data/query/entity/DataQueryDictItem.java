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
public class DataQueryDictItem extends BaseEntity{
    @Schema(title= "字典ID")
    @Excel(name="字典ID", width = 60)
    private String dictId;
    @Schema(title= "键值")
    @Excel(name="键值", width = 60)
    private String itemKey;
    @Schema(title= "元素值")
    @Excel(name="元素值", width = 60)
    private String itemValue;
}
