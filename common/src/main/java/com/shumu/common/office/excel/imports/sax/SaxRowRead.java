package com.shumu.common.office.excel.imports.sax;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shumu.common.office.excel.imports.param.ImportParam;
import com.shumu.common.office.excel.param.ColumnParam;
import com.shumu.common.util.DateUtil;

/**
 * @author Li
 * @since 23:26 2021/8/16 0016
 */
@Slf4j
public class SaxRowRead<T> {
    /** 需要返回的数据 */
    private List<T> list = new ArrayList<>();
    /** 导入参数 */
    private ImportParam importParam;
    /** 导出的对象 */
    private Class<T> entityClass;
    /** 列表头对应关系 */
    private Map<Integer, ColumnParam> titleMap = new HashMap<>();

    /**
     * 构造方法
     * 
     * @param importParam 导入参数
     * @param entityClass 实体类类型
     */
    public SaxRowRead(ImportParam importParam, Class<T> entityClass) {
        this.entityClass = entityClass;
        this.importParam = importParam;
    }

    /**
     * 获取返回数据
     * 
     * @return
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 读取行数据
     * 
     * @param index 行号
     * @param data  行数据
     */
    public void parse(int index, List<SaxReadCellEntity> data) {
        try {
            if (data == null || data.size() == 0) {
                return;
            }
            // 标题行跳过
            if (index < importParam.getTitleRows()) {
                return;
            }
            // 表头行
            if (index < importParam.getTitleRows() + importParam.getHeadRows()) {
                addHeadData(data);
            } else {
                addListData(data);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 添加表头信息
     * 
     * @param data 行数据
     */
    private void addHeadData(List<SaxReadCellEntity> data) {
        List<ColumnParam> params = importParam.getColumnParams();
        for (int i = 0; i < data.size(); i++) {
            SaxReadCellEntity entity = data.get(i);
            String title = entity.getValue().toString();
            if (null != title && !"".equals(title)) {
                ColumnParam param = params.get(i);
                if (title.equals(param.getTitle())) {
                    titleMap.put(i, param);
                } else {
                    if (param.getOrder() == i) {
                        titleMap.put(i, param);
                    }
                    int finalI = i;
                    params.forEach(item -> {
                        if (title.equals(item.getTitle())) {
                            titleMap.put(finalI, item);
                        }
                    });
                }
            }
        }
    }

    /**
     * 添加行数据
     * 
     * @param data 行数据
     * @throws Exception
     */
    private void addListData(List<SaxReadCellEntity> data) throws Exception {
        T entity = null;
        Map<String, Object> map = new HashMap<>(16);
        if (null != entityClass && entityClass.getSimpleName().contains(Map.class.getSimpleName())) {
            entity = entityClass.getDeclaredConstructor().newInstance();
        } 
        for (int i = 0; i < data.size(); i++) {
            SaxReadCellEntity saxEntity = data.get(i);
            CellValueType type = saxEntity.getCellType();
            Object value = saxEntity.getValue();
            ColumnParam param = titleMap.get(i);
            value = getFieldValue(value, type, param);
            if (null == entityClass || !entityClass.getSimpleName().contains(Map.class.getSimpleName())) {
                map.put(param.getKey(), value);
            } else {
                Field field = param.getField();
                field.setAccessible(true);
                field.set(entity, value);
            }
        }
    }

    /**
     * 获取日期类型字段类型的数据
     * 
     * @param value     数值
     * @param fieldType 字段类型
     * @return
     */
    private Object getDateFieldValue(Object value, String fieldType) {
        Object val = null;
        switch (fieldType) {
            case "LocalDateTime":
                val = value;
                break;
            case "Date":
                val = DateUtil.toDate((LocalDateTime) value);
                break;
            default:
                val = ((LocalDateTime) value).toString();
                break;
        }
        return val;
    }

    /**
     * 获取数字类型字段类型的数据
     * 
     * @param value     数值
     * @param fieldType 字段类型
     * @return
     */
    private Object getNumberFieldValue(Object value, String fieldType) {
        Object val = null;
        switch (fieldType) {
            case "BigDecimal":
                val = (BigDecimal) value;
                break;
            case "Double":
                val = ((BigDecimal) value).doubleValue();
                break;
            case "Long":
                val = ((BigDecimal) value).longValue();
                break;
            case "Integer":
                val = ((BigDecimal) value).intValue();
                break;
            case "Short":
                val = ((BigDecimal) value).shortValue();
                break;
            case "Byte":
                val = ((BigDecimal) value).byteValue();
                break;
            case "Date":
                val = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(((BigDecimal) value).doubleValue());
                break;
            case "LocalDateTime":
                val = org.apache.poi.ss.usermodel.DateUtil.getLocalDateTime(((BigDecimal) value).doubleValue());
                break;
            default:
                val = ((BigDecimal) value).toString();
                break;
        }
        return val;
    }

    /**
     * 获取字符串类型字段类型的数据
     * 
     * @param value     数值
     * @param fieldType 字段类型
     * @param param     参数
     * @return
     */
    private Object getStringFieldValue(Object value, String fieldType, ColumnParam param) {
        Object val = null;
        switch (fieldType) {
            case "String":
                val = value;
                break;
            case "BigDecimal":
                val = (BigDecimal) value;
                break;
            case "Double":
                val = Double.valueOf(value.toString());
                break;
            case "Long":
                val = Long.valueOf(value.toString());
                break;
            case "Integer":
                val = Integer.valueOf(value.toString());
                break;
            case "Short":
                val = Short.valueOf(value.toString());
                break;
            case "Byte":
                val = Byte.valueOf(value.toString());
                break;
            case "Date":
                if (null != param.getFormat() && !"".equals(param.getFormat())) {
                    val = DateUtil.str2Date(value.toString(), DateTimeFormatter.ofPattern(param.getFormat()));
                } else {
                    val = DateUtil.str2Date(value.toString());
                }
                break;
            case "LocalDateTime":
                if (null != param.getFormat() && !"".equals(param.getFormat())) {
                    val = DateUtil.str2LocalDateTime(value.toString(),
                            DateTimeFormatter.ofPattern(param.getFormat()));
                } else {
                    val = DateUtil.str2LocalDateTime(value.toString());
                }
                break;
            default:
                break;
        }
        return val;
    }

    /**
     * 获取字段对应值
     * 
     * @param value    数值
     * @param cellType 单元格类型
     * @param param    列参数
     * @return
     */
    private Object getFieldValue(Object value, CellValueType cellType, ColumnParam param) {
        Object val = null;
        String fieldType = param.getField().getType().getSimpleName();
        switch (cellType) {
            case DATE:
                getDateFieldValue(value, fieldType);
                break;
            case NUMBER:
                getNumberFieldValue(value, fieldType);
                break;
            case BOOLEAN:
                if (fieldType.equals(Boolean.class.getSimpleName())) {
                    val = value;
                } else {
                    val = ((Boolean) value).toString();
                }
                break;
            case STRING:
                getStringFieldValue(value, fieldType, param);
                break;
            default:
                break;
        }
        return val;
    }
}
