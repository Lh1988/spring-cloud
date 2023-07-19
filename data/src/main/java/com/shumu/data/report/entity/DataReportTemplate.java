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
@Schema(title= "报告模板")
public class DataReportTemplate extends BaseEntity {
    @Schema(title= "模板名称")
    @Excel(name="模板名称", width = 60)
    private String templateName;
    @Schema(title= "报告标题")
    @Excel(name="报告标题", width = 60)
    private String reportTitle;
    @Schema(title= "模板类型")
    @Excel(name="模板类型", width = 60, replace = {"0:Word","1:Html"})
    private Integer templateType;
    @Schema(title= "小数位数")
    @Excel(name="小数位数", width = 60)
    private Integer decimalDigits;
    @Schema(title= "模板文件")
    @Excel(name="模板文件", width = 60)
    private String templateFile;
    @Schema(title= "时间类型")
    @Excel(name="时间类型", width = 60, replace = {"0:月度","1:季度","2:年度","3:日报","4:周报","5:月周","6:定期","7:专题"})
    private Integer dateType;
    @Schema(title= "日期条件")
    @Excel(name="日期条件", width = 60)
    private String reportDate;
    @Schema(title= "年度条件")
    @Excel(name="年度条件", width = 60)
    private String reportYear;
    @Schema(title= "月度条件")
    @Excel(name="月度条件", width = 60)
    private String reportMonth;
    @Schema(title= "季度条件")
    @Excel(name="季度条件", width = 60)
    private String reportQuarter;
    @Schema(title= "周条件")
    @Excel(name="周条件", width = 60)
    private String reportWeek;
    @Schema(title= "日条件")
    @Excel(name="日条件", width = 60)
    private String reportDay;
    @Schema(title= "全局条件")
    @Excel(name="全局条件", width = 60)
    private String reportCondition;
}
