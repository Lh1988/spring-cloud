package com.shumu.common.office.excel.imports.sax;

/**
 * Cell 值类型
 * @author Li
 * @since 22:09 2021/8/16 0016
 */
public enum CellValueType {
    /**布尔类型*/
    BOOLEAN,
    /**错误类型*/
    ERROR,
    FORMULA,
    STRING,
    NUMBER,
    DATE,
    TElEMENT,
    NULL,
    NONE
}
