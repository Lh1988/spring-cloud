package com.shumu.data.analysis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@Schema(title= "分析报告")
@TableName("data_analysis_report")
public class DataAnalysisReport extends BaseEntity {
    @Schema(title= "报告名称")
    @Excel(name="报告名称", width = 60)
    private String reportName;
    @Schema(title= "报告标题")
    @Excel(name="报告标题", width = 60)
    private String reportTitle;
    @Schema(title= "报告类型")
    @Excel(name="报告类型", width = 60)
    private Integer reportType;
    @Schema(title= "报告模板")
    @Excel(name="报告模板", width = 60)
    private String reportTemplate;
    @Schema(title= "数据集")
    @Excel(name="数据集", width = 60)
    private String dataset;
}
