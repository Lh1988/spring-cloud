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
@Schema(title= "报告内容")
@TableName("data_analysis_content")
public class DataAnalysisContent extends BaseEntity {
    @Schema(title= "项目ID")
    @Excel(name="项目ID", width = 60)
    private String projectId;
    @Schema(title= "上级ID")
    @Excel(name="上级ID", width = 60)
    private String parentId;
    @Schema(title= "内容代码")
    @Excel(name="内容代码", width = 60)
    private String contentCode;
    /**
     * 0标题，1一级标题，2二级标题，3三级标题，4四级标题，5段落，6句子，7表格，8图表，9图片，10注解
     */
    @Schema(title= "内容类型")
    @Excel(name="内容类型", width = 60)
    private Integer contentType;
    @Schema(title= "内容描述")
    @Excel(name="内容描述", width = 60)
    private String contentDesc;
    @Schema(title= "内容格式")
    @Excel(name="内容格式", width = 60)
    private String contentStyle;
    @Schema(title= "序号")
    @Excel(name="序号", width = 60)
    private String orderIndex;
}
