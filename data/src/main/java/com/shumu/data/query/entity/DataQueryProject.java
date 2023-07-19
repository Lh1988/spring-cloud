package com.shumu.data.query.entity;

import com.shumu.common.base.entity.BaseEntity;
import com.shumu.common.office.excel.annotation.Excel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @description: 
* @author: Li
* @date: 2023-05-29
*/
@Data
@EqualsAndHashCode(callSuper=false)
@Schema(title= "查询项目")
public class DataQueryProject extends BaseEntity {
    @Schema(title= "项目名称")
    @Excel(name="项目名称", width = 60)
    private String projectName;
    @Schema(title= "项目描述")
    @Excel(name="项目描述", width = 60)
    private String projectDesc;
}
