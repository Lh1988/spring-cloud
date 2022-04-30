package com.shumu.common.office.excel.param;

import lombok.Data;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 对应Excel列的参数
 * 
 * @author Li
 * @since 17:28 2021/8/6 0006
 */
@Data
public class ColumnParam {
    /** 实体类字段名或Map键值---- */
    private String key;
    /** Excel列表头名---------- */
    private String title;
    /** Excel内列顺序---------- */
    private int order;
    /** Excel列宽------------- */
    private int width;
    /** 时间字段格式----------- */
    private String format;
    /** 前缀------------------ */
    private String prefix;
    /** 后缀------------------ */
    private String suffix;
    /** 值替换---------------- */
    private Map<String, String> replace;
    /** 对应实体类字段--------- */
    private Field field;
}
