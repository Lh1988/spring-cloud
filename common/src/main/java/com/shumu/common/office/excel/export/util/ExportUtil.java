package com.shumu.common.office.excel.export.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shumu.common.office.excel.annotation.Excel;
import com.shumu.common.office.excel.constant.ExcelConstant;
import com.shumu.common.office.excel.export.param.ExportParam;
import com.shumu.common.office.excel.param.CellStyleParam;
import com.shumu.common.office.excel.param.ColumnParam;
import com.shumu.common.office.excel.util.ParamUtil;
import com.shumu.common.util.DateUtil;
import com.shumu.common.util.EntityUtil;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

/**
 * @Description: excel导出工具类
 * @Author: Li
 * @Date: 2022-01-06
 * @LastEditTime: 2022-01-06
 * @LastEditors: Li
 */
public class ExportUtil {
    /**
     * 创建数据量较大的excel，多sheet，实体类
     * 
     * @param <T>          实体类类型
     * @param workbook     工作簿
     * @param param        导出参数
     * @param queryWrapper 查询
     * @param service      服务
     */
    public static <T> void createExcel(Workbook workbook, ExportParam param, QueryWrapper<T> queryWrapper,
            IService<T> service) {
        if (param.getColumns() == null || param.getColumns().isEmpty()) {
            return;
        }

        Sheet sheet = workbook.createSheet(param.getSheetName());

        int rowStartIndex = createSheet(workbook, sheet, param);
        int colStartIndex = param.getStartColumn();

        CellStyle style = null;
        if (param.getTableData() != null) {
            style = ParamUtil.getCellStyleByParam(workbook, param.getTableData());
        }

        List<ColumnParam> columns = param.getColumns();

        int sheetIndex = 0;
        int dataStartRow = rowStartIndex;
        int dataEndRow = rowStartIndex;
        int c = (int) service.count(queryWrapper);
        int total = 0;

        int maxRows = workbook.getSpreadsheetVersion().getMaxRows();
        do {
            Page<T> page = new Page<>(c, 5000);
            IPage<T> pageData = service.page(page, queryWrapper);
            List<T> data = pageData.getRecords();

            if (total > maxRows - rowStartIndex - 1) {
                dataEndRow = maxRows - 2;
                total = total - maxRows + rowStartIndex + 1;
            } else {
                dataEndRow = total + rowStartIndex - 1;
                total = 0;
            }

            if (total > 0) {
                sheetIndex++;
                Sheet cloneSheet = workbook.cloneSheet(workbook.getSheetIndex(sheet));
                workbook.setSheetName(workbook.getSheetIndex(cloneSheet), param.getSheetName() + sheetIndex);
            }

            for (int i = dataStartRow; i <= dataEndRow; i++) {
                Row row = sheet.createRow(i);
                T value = data.get(c);
                createEntityRow(row, columns, value, style, colStartIndex);
                c++;
            }

            if (total > 0) {
                sheet = workbook.getSheet(param.getSheetName() + sheetIndex);
            }

        } while (total > 0);

    }

    /**
     * 生成单sheet页Excel，数据量不大，数据类型为实体类或Map
     * 
     * @param <T>      实体类或Map
     * @param workbook 工作簿
     * @param param    导出参数
     * @param data     导出数据
     */
    @SuppressWarnings("all")
    public static <T> void createExcel(Workbook workbook, ExportParam param, List<T> data) {
        if (param.getColumns() == null || param.getColumns().isEmpty()) {
            return;
        }

        Sheet sheet = workbook.createSheet(param.getSheetName());

        int rowStartIndex = createSheet(workbook, sheet, param);
        int colStartIndex = param.getStartColumn();

        CellStyle style = null;
        if (param.getTableData() != null) {
            style = ParamUtil.getCellStyleByParam(workbook, param.getTableData());
        }

        List<ColumnParam> columns = param.getColumns();

        int dataStartRow = rowStartIndex;
        int dataEndRow = rowStartIndex + data.size() - 1;
        String dataType = data.get(0).getClass().getSimpleName();
        if (dataType.contains(Map.class.getSimpleName())) {
            for (int i = dataStartRow; i <= dataEndRow; i++) {
                Row row = sheet.createRow(i);
                Map<String, Object> value = (Map<String, Object>) data.get(i - dataStartRow);
                createMapRow(row, columns, value, style, colStartIndex);
            }
        } else {
            for (int i = dataStartRow; i <= dataEndRow; i++) {
                Row row = sheet.createRow(i);
                T value = data.get(i - dataStartRow);
                createEntityRow(row, columns, value, style, colStartIndex);
            }
        }
    }

    /**
     * 创建sheet页，包含表标题与表头
     * 
     * @param workbook
     * @param sheet
     * @param param
     * @return
     */
    private static int createSheet(Workbook workbook, Sheet sheet, ExportParam param) {
        List<ColumnParam> columns = param.getColumns();

        int colStartIndex = param.getStartColumn();
        int rowStartIndex = param.getStartRow();

        if (param.getFirstTitle() != null) {
            createTitleRow(workbook, sheet, param.getFirstTitle(), param.getFirstTitleText(), rowStartIndex,
                    colStartIndex, columns.size());
            rowStartIndex++;
        }

        if (param.getSecondTitle() != null) {
            createTitleRow(workbook, sheet, param.getSecondTitle(), param.getSecondTitleText(), rowStartIndex,
                    colStartIndex, columns.size());
            rowStartIndex++;
        }

        columns.sort(Comparator.comparing(ColumnParam::getOrder));
        createTableHead(workbook, sheet, columns, param.getTableHead(), rowStartIndex, colStartIndex);
        rowStartIndex++;

        return rowStartIndex;
    }

    /**
     * 解决火狐乱码问题
     * 
     * @param response
     * @param name
     * @param agent
     */
    public static void setResponse(HttpServletResponse response, String name, String agent) {
        response.setContentType("application/ms-excel; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        if (null != agent) {
            agent = agent.toLowerCase();
            try {
                if (agent.contains(ExcelConstant.FIRE_FOX)) {
                    response.setHeader("content-disposition",
                            String.format("attachment;filename*=utf-8'zh_cn'%s", URLEncoder.encode(name, "utf-8")));
                } else {
                    response.setHeader("content-disposition",
                            "attachment;filename=" + URLEncoder.encode(name, "utf-8"));
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建标题行
     * 
     * @param workbook
     * @param sheet
     * @param param
     * @param text
     * @param rowStar
     * @param colStart
     * @param colspan
     */
    private static void createTitleRow(Workbook workbook, Sheet sheet, CellStyleParam param, String text, int rowStar,
            int colStart, int colspan) {
        // 创建标题行并设置行高
        Row row = sheet.createRow(rowStar);
        if (param.getHeight() != null) {
            row.setHeight(param.getHeight());
        }
        // 创建左起第一个单元格，设置格式与内容，合并后
        Cell cell = row.createCell(colStart);
        CellStyle style = ParamUtil.getCellStyleByParam(workbook, param);
        cell.setCellStyle(style);
        cell.setCellValue(text);
        // 创建合并区域，横跨所有内容列
        CellRangeAddress range = new CellRangeAddress(rowStar, rowStar, colStart, colStart + colspan - 1);
        sheet.addMergedRegion(range);
        // 设置边框
        if (param.getLeftBorderStyle() != null) {
            RegionUtil.setBorderLeft(style.getBorderLeft(), range, sheet);
        }
        if (param.getTopBorderStyle() != null) {
            RegionUtil.setBorderTop(style.getBorderTop(), range, sheet);
        }
        if (param.getRightBorderStyle() != null) {
            RegionUtil.setBorderRight(style.getBorderRight(), range, sheet);
        }
        if (param.getBottomBorderStyle() != null) {
            RegionUtil.setBorderBottom(style.getBorderBottom(), range, sheet);
        }
        if (param.getLeftBorderColor() != null) {
            RegionUtil.setLeftBorderColor(style.getLeftBorderColor(), range, sheet);
        }
        if (param.getTopBorderColor() != null) {
            RegionUtil.setTopBorderColor(style.getTopBorderColor(), range, sheet);
        }
        if (param.getRightBorderColor() != null) {
            RegionUtil.setRightBorderColor(style.getRightBorderColor(), range, sheet);
        }
        if (param.getBottomBorderColor() != null) {
            RegionUtil.setBottomBorderColor(style.getBottomBorderColor(), range, sheet);
        }
    }

    /**
     * 创建表头
     * 
     * @param workbook
     * @param sheet
     * @param columns
     * @param param
     * @param rowStar
     * @param colStart
     */
    private static void createTableHead(Workbook workbook, Sheet sheet, List<ColumnParam> columns, CellStyleParam param,
            int rowStar, int colStart) {
        Row row = sheet.createRow(rowStar);
        CellStyle style = null;
        if (param != null) {
            style = ParamUtil.getCellStyleByParam(workbook, param);
        }
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).getWidth() != 0) {
                sheet.setColumnWidth(colStart + i, columns.get(i).getWidth());
            }
            Cell cell = row.createCell(colStart + i);
            cell.setCellValue(columns.get(i).getTitle());
            if (style != null) {
                cell.setCellStyle(style);
            }
        }
    }

    /**
     * 根据实体类对象创建数据行
     * 
     * @param <T>
     * @param row
     * @param columns
     * @param entity
     * @param style
     * @param colStart
     */
    private static <T> void createEntityRow(Row row, List<ColumnParam> columns, T entity, CellStyle style,
            int colStart) {
        for (int i = 0; i < columns.size(); i++) {
            Cell cell = row.createCell(i + colStart);
            if (style != null) {
                cell.setCellStyle(style);
            }
            Field field = columns.get(i).getField();
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(entity);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            value = getColumnValue(value, columns.get(i));
        }
    }

    /**
     * 根据Map数据创建数据行
     * 
     * @param row
     * @param columns
     * @param map
     * @param style
     * @param colStart
     */
    private static void createMapRow(Row row, List<ColumnParam> columns, Map<String, Object> map, CellStyle style,
            int colStart) {
        for (int i = 0; i < columns.size(); i++) {
            Cell cell = row.createCell(i + colStart);
            if (style != null) {
                cell.setCellStyle(style);
            }
            Object value = map.get(columns.get(i).getKey());
            value = getColumnValue(value, columns.get(i));
        }
    }

    /**
     * 获取对应列的数据
     * 
     * @param value
     * @param columnParam
     * @return
     */
    private static Object getColumnValue(Object value, ColumnParam columnParam) {
        if (null != columnParam.getFormat() && !"".equals(columnParam.getFormat())) {
            if (value instanceof LocalDateTime) {
                value = DateUtil.localDateTime2Str((LocalDateTime) value,
                        DateTimeFormatter.ofPattern(columnParam.getFormat()));
            } else if (value instanceof Date) {
                value = DateUtil.date2Str((Date) value, DateTimeFormatter.ofPattern(columnParam.getFormat()));
            }
        }
        if (null != columnParam.getReplace() && !columnParam.getReplace().isEmpty()) {
            Map<String, String> replaceMap = columnParam.getReplace();
            value = replaceMap.get(value.toString());
        }
        if (null != columnParam.getPrefix() && !"".equals(columnParam.getPrefix())) {
            value = columnParam.getPrefix() + value;
        }
        if (null != columnParam.getSuffix() && !"".equals(columnParam.getSuffix())) {
            value = value + columnParam.getSuffix();
        }
        return value;
    }

    public static List<ColumnParam> getExcelColumnParam(Field[] fields) {
        List<ColumnParam> list = new ArrayList<>();
        boolean needOrder = false;
        for (Field field : fields) {
            if (field.getAnnotation(Excel.class) != null) {
                ColumnParam columnParam = new ColumnParam();
                columnParam.setField(field);
                Excel excel = field.getAnnotation(Excel.class);
                columnParam.setKey(field.getName());
                columnParam.setTitle("".equals(excel.name()) ? field.getName() : excel.name());
                if (excel.order() != 0) {
                    needOrder = true;
                    columnParam.setOrder(excel.order());
                }
                columnParam.setPrefix(excel.prefix());
                columnParam.setSuffix(excel.suffix());
                if (excel.replace().length > 0) {
                    String[] replaces = excel.replace();
                    Map<String, String> map = new HashMap<>(16);
                    for (String replace : replaces) {
                        if (replace.contains(":")) {
                            map.put(replace.split(":")[0].trim(), replace.split(":")[1]);
                        }
                    }
                    columnParam.setReplace(map);
                }
                columnParam.setFormat(excel.format());
                if (excel.width() > 0) {
                    columnParam.setWidth(excel.width());
                }
                list.add(columnParam);
            }
        }
        if (needOrder) {
            list.sort(Comparator.comparingInt(ColumnParam::getOrder));
        }
        return list;
    }

    public static <T> ExportParam getDefaultEntityExportParam(T entity) {
        ExportParam param = new ExportParam();
        /* 配置导出文件列参数：从字段注解中获得内容 */
        List<ColumnParam> columns = getExcelColumnParam(EntityUtil.getEntityClassFields(entity.getClass()));
        param.setColumns(columns);
        /* 设置导出表跨列合并的主标题title */
        CellStyleParam firstTitleStyle = new CellStyleParam();
        setDefaultBorder(firstTitleStyle);
        firstTitleStyle.setAlign(HorizontalAlignment.CENTER.getCode());
        param.setFirstTitle(firstTitleStyle);
        param.setFirstTitleText("导出数据");
        /* 设置导出表跨列合并的次级标题title */
        CellStyleParam secondTitleStyle = new CellStyleParam();
        setDefaultBorder(secondTitleStyle);
        secondTitleStyle.setAlign(HorizontalAlignment.RIGHT.getCode());
        param.setSecondTitle(secondTitleStyle);
        param.setSecondTitleText("导出时间:" + LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        /* 设置导出表表头head */
        CellStyleParam headStyle = new CellStyleParam();
        setDefaultBorder(headStyle);
        headStyle.setAlign(HorizontalAlignment.CENTER.getCode());
        param.setTableHead(headStyle);
         /* 设置导出表数据单元格格式*/
         CellStyleParam cellStyle = new CellStyleParam();
         setDefaultBorder(cellStyle);
         param.setTableData(cellStyle);
         return param;
    }

    private static void setDefaultBorder(CellStyleParam excelStyleParam) {
        excelStyleParam.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        excelStyleParam.setLeftBorderStyle(BorderStyle.THIN.getCode());
        excelStyleParam.setTopBorderColor(IndexedColors.BLACK.getIndex());
        excelStyleParam.setTopBorderStyle(BorderStyle.THIN.getCode());
        excelStyleParam.setRightBorderColor(IndexedColors.BLACK.getIndex());
        excelStyleParam.setRightBorderStyle(BorderStyle.THIN.getCode());
        excelStyleParam.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        excelStyleParam.setBottomBorderStyle(BorderStyle.THIN.getCode());
    }
}
