package com.shumu.common.office.excel.imports.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import com.shumu.common.office.excel.imports.param.ImportParam;
import com.shumu.common.office.excel.imports.sax.SaxRowRead;
import com.shumu.common.office.excel.imports.sax.SheetHandler;
import com.shumu.common.office.excel.param.ColumnParam;
import com.shumu.common.util.DateUtil;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * @Description: 导入excel数据
 * @Author: Li
 * @Date: 2022-01-11
 * @LastEditTime: 2022-01-11
 * @LastEditors: Li
 */
public class ImportUtil {
    /**
     * 导入Excel数据获取实体类列表
     * 
     * @param <T>
     * @param inputStream
     * @param importParam
     * @param entityClass
     * @return
     */
    @SuppressWarnings("all")
    public static <T> List<T> importExcel(InputStream inputStream, ImportParam importParam, Class<T> entityClass) {
        List<T> data = new ArrayList<>();
        Workbook workbook = null;
        if (!(inputStream.markSupported())) {
            inputStream = new PushbackInputStream(inputStream, 8);
        }
        try {
            workbook = WorkbookFactory.create(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (workbook != null) {
            Iterator<Sheet> sheets = workbook.sheetIterator();
            while (sheets.hasNext()) {
                Sheet sheet = sheets.next();
                List<?> list;
                if (entityClass.getSimpleName().contains(Map.class.getSimpleName())) {
                    list = getSheetMapData(sheet, importParam);
                } else {
                    list = getSheetEntityData(sheet, importParam, entityClass);
                }
                if (!list.isEmpty()) {
                    list.forEach(item -> data.add((T) item));
                }
            }
        }

        return data;
    }

    /**
     * 通过sax导入Excel数据获取实体类列表
     * 
     * @param <T>
     * @param inputStream
     * @param importParam
     * @param entityClass
     * @return
     * @throws ParserConfigurationException
     */
    public static <T> List<T> importExcelBySax(InputStream inputStream, ImportParam importParam, Class<T> entityClass)
            throws ParserConfigurationException {
        try {
            OPCPackage opcPackage = OPCPackage.open(inputStream);
            XSSFReader xssfReader = new XSSFReader(opcPackage);
            SharedStringsTable sst = xssfReader.getSharedStringsTable();

            SaxRowRead<T> rowRead = new SaxRowRead<>(importParam, entityClass);
            XMLReader parser = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            ContentHandler handler = new SheetHandler<T>(sst, rowRead);
            parser.setContentHandler(handler);

            Iterator<InputStream> sheets = xssfReader.getSheetsData();
            while (sheets.hasNext()) {
                InputStream sheet = sheets.next();
                InputSource sheetSource = new InputSource(sheet);
                parser.parse(sheetSource);
                sheet.close();
            }
            return rowRead.getList();
        } catch (IOException | OpenXML4JException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取整sheet数据
     * 
     * @param <T>
     * @param sheet
     * @param importParam
     * @param entityClass
     * @return
     */
    private static <T> List<T> getSheetEntityData(Sheet sheet, ImportParam importParam, Class<T> entityClass) {
        List<T> list = new ArrayList<>();
        Iterator<Row> rows = sheet.rowIterator();
        Row row = null;
        for (int j = 0; j < importParam.getTitleRows() + importParam.getHeadRows() - 1; j++) {
            row = rows.next();
        }
        if (null == row) {
            return list;
        }
        int start = row.getFirstCellNum();
        int end = row.getLastCellNum();
        row = rows.next();
        if (null != row) {
            List<ColumnParam> fields = importParam.getColumnParams();
            Map<Integer, ColumnParam> titleMap = getTitleMap(row, fields);
            while (rows.hasNext()) {
                row = rows.next();
                T entity = getEntityData(row, titleMap, entityClass, start, end);
                if (null != entity) {
                    list.add(entity);
                }
            }
        }
        return list;
    }

    /**
     * 获取行数据
     * 
     * @param <T>
     * @param row
     * @param titleMap
     * @param entityClass
     * @param start
     * @param end
     * @return
     */
    private static <T> T getEntityData(Row row, Map<Integer, ColumnParam> titleMap, Class<T> entityClass, int start,
            int end) {
        T entity = null;
        try {
            entity = entityClass.getDeclaredConstructor().newInstance();
            for (int i = start; i <= end; i++) {
                ColumnParam fieldParam = titleMap.get(i);
                if (null != fieldParam) {
                    Cell cell = row.getCell(i);
                    Object value = null;
                    if (null != cell) {
                        value = getCellValue(fieldParam, cell);
                    }
                    Field field = fieldParam.getField();
                    field.setAccessible(true);
                    field.set(entity, value);
                }
            }
        } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException
                | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return entity;
    }

    /**
     * 获取Map类型行数据
     * 
     * @param row
     * @param titleMap
     * @param start
     * @param end
     * @return
     */
    private static Map<String, Object> getMapData(Row row, Map<Integer, ColumnParam> titleMap, int start, int end) {
        Map<String, Object> map = new HashMap<>(16);
        for (int i = start; i <= end; i++) {
            ColumnParam fieldParam = titleMap.get(i);
            if (null != fieldParam) {
                Cell cell = row.getCell(i);
                Object value = null;
                if (null != cell) {
                    value = getCellValue(fieldParam, cell);
                }
                map.put(fieldParam.getKey(), value);
            }
        }
        return map;
    }

    /**
     * 获取整sheet的Map类型数据
     * 
     * @param sheet
     * @param importParam
     * @return
     */
    private static List<Map<String, Object>> getSheetMapData(Sheet sheet, ImportParam importParam) {
        List<Map<String, Object>> list = new ArrayList<>();
        Iterator<Row> rows = sheet.rowIterator();
        Row row = null;
        for (int j = 0; j < importParam.getTitleRows() + importParam.getHeadRows() - 1; j++) {
            row = rows.next();
        }
        if (null == row) {
            return list;
        }
        int start = row.getFirstCellNum();
        int end = row.getLastCellNum();
        row = rows.next();
        if (null != row) {
            List<ColumnParam> fields = importParam.getColumnParams();
            Map<Integer, ColumnParam> titleMap = getTitleMap(row, fields);
            while (rows.hasNext()) {
                row = rows.next();
                Map<String, Object> map = getMapData(row, titleMap, start, end);
                if (!map.isEmpty()) {
                    list.add(map);
                }
            }
        }
        return list;
    }

    /**
     * 获取单元格数据
     * 
     * @param fieldParam
     * @param cell
     * @return
     */
    private static Object getCellValue(ColumnParam fieldParam, Cell cell) {
        Object value = null;
        String type = fieldParam.getType();
        if(null!=fieldParam.getField()){
            type=fieldParam.getField().getType().getSimpleName();
        }
        switch (type) {
            case "Integer":
                if (cell.getCellType() == CellType.NUMERIC) {
                    value = (int) cell.getNumericCellValue();
                } else {
                    String val = getStringValue(cell);
                    val = getInitialValue(fieldParam, val);
                    value = null == val ? null : Integer.parseInt(val);
                }
                break;
            case "Double":
                if (cell.getCellType() == CellType.NUMERIC) {
                    value = cell.getNumericCellValue();
                } else {
                    String val = getStringValue(cell);
                    val = getInitialValue(fieldParam, val);
                    value = null == val ? null : Double.parseDouble(val);
                }
                break;
            case "Boolean":
                if (cell.getCellType() == CellType.BOOLEAN) {
                    value = cell.getBooleanCellValue();
                } else {
                    String val = getStringValue(cell);
                    val = getInitialValue(fieldParam, val);
                    value = null == val ? null : Boolean.parseBoolean(val);
                }
                break;
            case "Date":
                if (cell.getCellType() == CellType.NUMERIC) {
                    value = cell.getDateCellValue();
                } else {
                    String val = getStringValue(cell);
                    val = getInitialValue(fieldParam, val);
                    if (null != val) {
                        if (null != fieldParam.getFormat() && !"".equals(fieldParam.getFormat())) {
                            value = DateUtil.str2Date(val, DateTimeFormatter.ofPattern(fieldParam.getFormat()));
                        } else {
                            value = DateUtil.str2Date(val);
                        }
                    }
                }
                break;
            case "LocalDateTime":
                if (cell.getCellType() == CellType.NUMERIC) {
                    value = cell.getLocalDateTimeCellValue();
                } else {
                    String val = getStringValue(cell);
                    val = getInitialValue(fieldParam, val);
                    if (null != val) {
                        if (null != fieldParam.getFormat() && !"".equals(fieldParam.getFormat())) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(fieldParam.getFormat());
                            value = DateUtil.str2LocalDateTime(val, formatter);
                        }
                    }
                }
                break;
            case "String":
                String val = getStringValue(cell);
                value = getInitialValue(fieldParam, val);
                break;
            default:
                break;
        }
        return value;
    }

    /**
     * 获取表头信息
     * 
     * @param row
     * @param params
     * @return
     */
    public static Map<Integer, ColumnParam> getTitleMap(Row row, List<ColumnParam> params) {
        Map<Integer, ColumnParam> map = new HashMap<>(16);
        int start = row.getFirstCellNum();
        int end = row.getLastCellNum();
        int c = 0;
        for (int i = start; i < end + 1; i++) {
            Cell cell = row.getCell(i);
            if (null != cell) {
                String value = getStringValue(cell);
                if (null != value && !"".equals(value)) {
                    ColumnParam param = params.get(c);
                    if (value.equals(param.getTitle())) {
                        map.put(i, param);
                    } else {
                        if (param.getOrder() == c) {
                            map.put(i, param);
                        }
                        int finalI = i;
                        params.forEach(item -> {
                            if (value.equals(item.getTitle())) {
                                map.put(finalI, item);
                            }
                        });
                    }
                    c++;
                }
            }
        }
        return map;
    }

    /**
     * 字符串数据转换
     * 
     * @param cell
     * @return
     */
    public static String getStringValue(Cell cell) {
        Object obj = null;
        switch (cell.getCellType()) {
            case STRING:
                obj = cell.getStringCellValue();
                break;
            case BOOLEAN:
                obj = cell.getBooleanCellValue();
                break;
            case NUMERIC:
                obj = cell.getNumericCellValue();
                break;
            case FORMULA:
                obj = cell.getCellFormula();
                break;
            default:
                break;
        }
        return obj == null ? null : obj.toString().trim();
    }

    /**
     * 获取对应转换的原始数据
     * 
     * @param fieldParam
     * @param value
     * @return
     */
    private static String getInitialValue(ColumnParam fieldParam, String value) {
        if (null == value) {
            return null;
        }
        if (null != fieldParam.getSuffix() && !"".equals(fieldParam.getSuffix())) {
            String suffix = fieldParam.getSuffix();
            if (value.endsWith(suffix)) {
                value = value.substring(0, value.length() - suffix.length());
            }
        }
        if (null != fieldParam.getPrefix() && !"".equals(fieldParam.getPrefix())) {
            String prefix = fieldParam.getPrefix();
            if (value.startsWith(prefix)) {
                value = value.substring(prefix.length());
            }
        }
        if (null != fieldParam.getImportReplace() && !fieldParam.getImportReplace().isEmpty()) {
            Map<String, String> map = fieldParam.getImportReplace();
            if (map.containsKey(value)) {
               value = map.get(value);
            }
        }
        return value;
    }

}
