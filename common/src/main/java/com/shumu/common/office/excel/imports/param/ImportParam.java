package com.shumu.common.office.excel.imports.param;

import java.util.List;

import com.shumu.common.office.excel.param.ColumnParam;

import lombok.Data;
/**
* @Description: excel数据导入参数
* @Author: Li
* @Date: 2022-01-11
* @LastEditTime: 2022-01-11
* @LastEditors: Li
*/
@Data
public class ImportParam {
    private int titleRows = 2;
    private int headRows = 1;
    private List<ColumnParam> columnParams;
    
}
