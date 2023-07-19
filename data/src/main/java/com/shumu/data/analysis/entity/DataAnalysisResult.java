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
@Schema(title= "分析结果")
@TableName("data_analysis_result")
public class DataAnalysisResult extends BaseEntity {
    @Schema(title= "项目ID")
    @Excel(name="项目ID", width = 60)
    private String projectId;
    @Schema(title= "语句模板")
    @Excel(name="语句模板", width = 60)
    private String sentenceTemplate;
    @Schema(title= "语句条件")
    @Excel(name="语句条件", width = 60)
    private String conditionIds;
    @Schema(title= "结果判断")
    @Excel(name="结果判断", width = 60)
    private String resultJudge;
    @Schema(title= "语句结果")
    @Excel(name="语句结果", width = 60)
    private String resultIds;
    /**
     * 0:原值,1:占比,2:排名
     * 3:可比上年,4:可比增长,5:可比增速,6:可比拉动,7:可比贡献,8:可比排名
     * 9:同比上年,10:同比增长,11:同比增速,12:同比拉动,13:同比贡献,14:同比排名
     * 15:环比上期,16:环比增长,17:环比增速,18:环比拉动,19:环比贡献,20:环比排名
     * 21:平均增速
     */
    @Schema(title= "语句结果")
    @Excel(name="语句结果", width = 60)
    private Integer resultType;
    /**
     * 0:原值,1:对比,2:求和,3:列表,4:省略
     */
    @Schema(title= "结果展示")
    @Excel(name="结果展示", width = 60)
    private Integer resultShow;
    @Schema(title= "结果运算")
    @Excel(name="结果运算", width = 60)
    private String resultOperate;
    @Schema(title= "结果单位")
    @Excel(name="结果单位", width = 60)
    private Integer resultUnit;
    @Schema(title= "序号")
    @Excel(name="序号", width = 60)
    private Integer orderIndex;
}
