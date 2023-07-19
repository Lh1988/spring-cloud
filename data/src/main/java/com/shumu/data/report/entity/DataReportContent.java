package com.shumu.data.report.entity;

import com.shumu.common.base.entity.BaseEntity;
import com.shumu.common.office.excel.annotation.Excel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @description: 
* @author: Li
* @date: 2023-07-17
*/
@Data
@EqualsAndHashCode(callSuper=false)
@Schema(title= "报告语句")
public class DataReportContent extends BaseEntity{
    @Schema(title= "报告模板ID")
    @Excel(name="报告模板ID", width = 60)
    private String reportId;
    @Schema(title= "语句代码")
    @Excel(name="语句代码", width = 60)
    private String contentCode;
    @Schema(title= "语句模板")
    @Excel(name="语句模板", width = 60)
    private String contentTemplate;
    @Schema(title= "内容类型")
    @Excel(name="内容类型", width = 60)
    private Integer contentType;
    @Schema(title= "内容结果")
    @Excel(name="内容结果", width = 60)
    private String contentResult;
    @Schema(title= "列结果")
    @Excel(name="列结果", width = 60)
    private String columnResult;
    @Schema(title= "行结果")
    @Excel(name="行结果", width = 60)
    private String rowResult;
}
