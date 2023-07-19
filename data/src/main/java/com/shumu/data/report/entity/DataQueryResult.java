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
public class DataQueryResult  extends BaseEntity {
    @Schema(title= "分类指标")
    @Excel(name="分类指标", width = 60)
    private String classTargets;
    @Schema(title= "数值指标")
    @Excel(name="数值指标", width = 60)
    private String numberTargets;
    @Schema(title= "查询条件")
    @Excel(name="查询条件", width = 60)
    private String conditions;
    /**
     * 0 主结果
     * 1 上级结果
     * 2 排序列表
     * 3 对比结果
     * 4 对比上级
     * 5 对比列表
     */
    @Schema(title= "结果类型")
    @Excel(name="结果类型", width = 60)
    private String resultType;
     /**
     * 0 原值
     * 1 同比增速
     * 2 环比增速
     * 3 同比增量
     * 4 环比增量
     * 5 平均增速
     */
    @Schema(title= "取值类型")
    @Excel(name="取值类型", width = 60)
    private String valueType;
    @Schema(title= "取值参数")
    @Excel(name="取值参数", width = 60)
    private String valueParam;
}
