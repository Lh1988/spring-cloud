package com.shumu.common.office.excel.export.param;

import java.util.List;

import com.shumu.common.office.excel.param.CellStyleParam;
import com.shumu.common.office.excel.param.ColumnParam;

import lombok.Data;

/**
* @Description: 导出参数
* @Author: Li
* @Date: 2022-01-12
* @LastEditTime: 2022-01-12
* @LastEditors: Li
*/
@Data
public class ExportParam {
        /** 导出的Excel文件名-------------- */
        private String fileName = "data";
        /** 导出的Excel内Sheet名---------- */
        private String sheetName = "sheet";
        /** 内容开始行，从标题开始---------- */
        private int startRow = 0;
        /** 内容开始列，从标题开始---------- */
        private int startColumn = 0;
        /** 第一标题内容------------------ */
        private String firstTitleText = "数据导出";
        /** 第二标题内容------------------ */
        private String secondTitleText = "导出人: 管理员";
        /** 注脚内容--------------------- */
        private String tableFootText = "数据导出";
        /** 第一标题格式------------------ */
        private CellStyleParam firstTitle;
        /** 第二标题格式------------------ */
        private CellStyleParam secondTitle;
        /** 表头格式--------------------- */
        private CellStyleParam tableHead;
        /** 表主体格式------------------- */
        private CellStyleParam tableData;
        /** 注脚格式--------------------- */
        private CellStyleParam tableFoot;
        /** 导出列参数------------------- */
        private List<ColumnParam> columns;
}
