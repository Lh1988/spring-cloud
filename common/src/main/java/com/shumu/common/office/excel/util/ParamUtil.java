package com.shumu.common.office.excel.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import com.shumu.common.office.excel.annotation.Excel;
import com.shumu.common.office.excel.param.CellStyleParam;
import com.shumu.common.office.excel.param.ColumnParam;
/**
* @Description: 
* @Author: Li
* @Date: 2021-12-28
* @LastEditTime: 2021-12-28
* @LastEditors: Li
*/
public class ParamUtil {
    /**
     * 根据@Excel注解筛选要导出Excel的字段
     * 
     * @author Li
     * @param field 字段
     * @return ColumnParam
     */
    public static ColumnParam getColumnParamByField(Field field) {
        if (field.getAnnotation(Excel.class) != null) {
            ColumnParam columnParam = new ColumnParam();
            columnParam.setField(field);
            Excel excel = field.getAnnotation(Excel.class);
            columnParam.setKey(field.getName());
            columnParam.setTitle("".equals(excel.name()) ? field.getName() : excel.name());
            columnParam.setOrder(excel.order());
            columnParam.setPrefix(excel.prefix());
            columnParam.setSuffix(excel.suffix());
            if (excel.replace().length > 0) {
                String[] replaces = excel.replace();
                Map<String, String> mapExport = new HashMap<>(16);
                Map<String, String> mapImport = new HashMap<>(16);
                for (String replace : replaces) {
                    if (replace.contains(":")) {
                        mapExport.put(replace.split(":")[0].trim(), replace.split(":")[1]);
                        mapImport.put(replace.split(":")[1].trim(), replace.split(":")[0]);
                    }
                }
                columnParam.setExportReplace(mapExport);
                columnParam.setImportReplace(mapImport);
            }
            columnParam.setFormat(excel.format());
            if (excel.width() > 0) {
                columnParam.setWidth(excel.width());
            }
            if (!excel.isWrap()) {
                CellStyleParam style = new CellStyleParam();
                if (!excel.isWrap()) {
                    style.setWarp(excel.isWrap());
                }
            }
            return columnParam;
        }
        return null;
    }

    /**
     * 根据@Excel注解筛选要导出Excel的字段
     * 
     * @author Li
     * @param fields 字段数组
     * @return List<ColumnParam>
     */
    public static List<ColumnParam> getColumnParamList(Field[] fields) {
        List<ColumnParam> list = new ArrayList<>();
        boolean needOrder = false;
        for (Field field : fields) {
            ColumnParam columnParam = getColumnParamByField(field);
            if (columnParam != null) {
                if (columnParam.getOrder() != 0) {
                    needOrder = true;
                }
                list.add(columnParam);
            }
        }
        if (needOrder) {
            list.sort(Comparator.comparingInt(ColumnParam::getOrder));
        }
        return list;
    }

    public static Field[] getClassFields(Class<?> clazz) {
        List<Field> list = new ArrayList<>();
        Field[] fields;
        do {
            fields = clazz.getDeclaredFields();
            Collections.addAll(list, fields);
            clazz = clazz.getSuperclass();
        } while (clazz != Object.class && clazz != null);
        return list.toArray(fields);
    }

    public static List<ColumnParam> getColumnParamList(Class<?> clazz) {
        List<Field> list = new ArrayList<>();
        Field[] fields;
        do {
            fields = clazz.getDeclaredFields();
            Collections.addAll(list, fields);
            clazz = clazz.getSuperclass();
        } while (clazz != Object.class && clazz != null);
        boolean needOrder = false;
        List<ColumnParam> columns = new ArrayList<>();
        for (Field field : list) {
            ColumnParam columnParam = getColumnParamByField(field);
            if (columnParam != null) {
                if (columnParam.getOrder() != 0) {
                    needOrder = true;
                }
                columns.add(columnParam);
            }
        }
        if (needOrder) {
            columns.sort(Comparator.comparingInt(ColumnParam::getOrder));
        }
        return columns;
    }
    
    /**
     * 根据设置的参数获取poi的CellStyle
     * 
     * @param workbook
     * @param param
     * @return
     */
    public static CellStyle getCellStyleByParam(Workbook workbook, CellStyleParam param) {
        CellStyle style = workbook.createCellStyle();
        /* 字体样式 */
        if (param.getItalic() || param.getBold() || param.getUnderline() != null || param.getFontColor() != null
                || param.getFontName() != null || param.getFontHeight() != null) {
            Font font = workbook.createFont();
            if (param.getItalic()) {
                font.setItalic(param.getItalic());
            }
            if (param.getBold()) {
                font.setBold(param.getBold());
            }
            if (param.getUnderline() != null) {
                font.setUnderline(param.getUnderline());
            }
            if (param.getFontHeight() != null) {
                font.setFontHeight(param.getFontHeight());
            }
            if (param.getFontName() != null) {
                font.setFontName(param.getFontName());
            }
            if (param.getFontColor() != null) {
                font.setColor(param.getFontColor());
            }
            style.setFont(font);
        }
        /* 对齐与换行 */
        if (param.getAlign() != null) {
            style.setAlignment(HorizontalAlignment.forInt(param.getAlign()));
        }
        if (param.getVertical() != null) {
            style.setVerticalAlignment(VerticalAlignment.forInt(param.getVertical()));
        }
        if (param.getWarp()) {
            style.setWrapText(param.getWarp());
        }
        /* 单元格边框 */
        if (param.getBottomBorderColor() != null) {
            style.setBottomBorderColor(param.getBottomBorderColor());
        }
        if (param.getBottomBorderStyle() != null) {
            style.setBorderBottom(BorderStyle.valueOf(param.getBottomBorderStyle()));
        }
        if (param.getLeftBorderColor() != null) {
            style.setLeftBorderColor(param.getLeftBorderColor());
        }
        if (param.getLeftBorderStyle() != null) {
            style.setBorderLeft(BorderStyle.valueOf(param.getLeftBorderStyle()));
        }
        if (param.getTopBorderColor() != null) {
            style.setTopBorderColor(param.getTopBorderColor());
        }
        if (param.getTopBorderStyle() != null) {
            style.setBorderTop(BorderStyle.valueOf(param.getTopBorderStyle()));
        }
        if (param.getRightBorderColor() != null) {
            style.setRightBorderColor(param.getRightBorderColor());
        }
        if (param.getRightBorderStyle() != null) {
            style.setBorderRight(BorderStyle.valueOf(param.getRightBorderStyle()));
        }
        /* 背景颜色 */
        if (param.getColor() != null) {
            style.setFillBackgroundColor(param.getColor());
        }
        if (param.getDataFormat() != null) {
            style.setDataFormat(param.getDataFormat());
        }
        return style;
    }

}
