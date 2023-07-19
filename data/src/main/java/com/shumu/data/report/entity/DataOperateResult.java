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
@Schema(title= "查询结果")
public class DataOperateResult extends BaseEntity{
    @Schema(title= "查询结果")
    @Excel(name="查询结果", width = 60)
    private String resultIds;
    /**
     * 0 原值
     * 1 占比
     * 2 拉动
     * 3 贡献
     * 4 排名
     * 5 对比
     */
    @Schema(title= "结果类型")
    @Excel(name="结果类型", width = 60)
    private Integer resultType;
    /**
     * 0 原值
     * 1 相加
     * 2 相减
     * 3 相乘
     * 4 相除
     * 5 增速
     */
    @Schema(title= "结果运算")
    @Excel(name="结果运算", width = 60)
    private Integer resultOperate;
    @Schema(title= "运算参数")
    @Excel(name="运算参数", width = 60)
    private String operateParam;
    @Schema(title= "结果单位")
    @Excel(name="结果单位", width = 60)
    private Double resultUnit;
    @Schema(title= "结果判断")
    @Excel(name="结果判断", width = 60)
    private String resultJudge;
    /**
     * 0 分别
     * 1 合计
     * 2 首位
     * 3 前三合计
     * 4 前三分别
     */
    @Schema(title= "列表展示")
    @Excel(name="列表展示", width = 60)
    private Integer listShow;
}
