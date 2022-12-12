package com.shumu.common.query.util;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.shumu.common.constant.CommonConstant;
import com.shumu.common.query.condition.QueryCondition;
import com.shumu.common.query.enums.QueryRuleEnum;
import com.shumu.common.util.DateUtil;

import org.apache.commons.beanutils.PropertyUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 装配适用于前端实体类列表查询的Mybatis Plus查询条件
 * @Author: Li
 * @Date: 2021-12-29
 * @LastEditTime: 2021-12-29
 * @LastEditors: Li
 */
@Slf4j
public class QueryGenerator {
    private static final int DATE_LENGTH = 10;
    private static final String BEGIN = "_begin";
    private static final String END = "_end";
    private static final String FILTER = "_filter";
    /** 数字类型字段，拼接此后缀 接受多值参数 */
    private static final String MULTI = "_MultiString";
    private static final String STAR = "*";
    private static final String COMMA = ",";
    /** 页面带有规则值查询，空格作为分隔符 */
    private static final String QUERY_SEPARATE_KEYWORD = " ";
    /** 高级查询前端传来的参数名 */
    private static final String SUPER_QUERY_PARAMS = "superQueryParams";
    /** 单引号 */
    public static final String SQL_SQ = "'";
    /** 排序列 */
    private static final String ORDER_COLUMN = "sortField";
    /** 排序方式 */
    private static final String ORDER_TYPE = "sortOrder";
    private static final String ORDER_TYPE_ASC = "ASC";

    private static final String NULL = "null";
    private static final String NOT_NULL = "not null";
    /** mysql 模糊查询之特殊字符下划线 （_、\） */
    public static final String LIKE_MYSQL_SPECIAL_STRS = "_,%";

    /**
     * 组装Mybatis Plusmy 查询条件公共方法
     * @param <T>
     * @param searchObj
     * @param parameterMap
     * @return QueryWrapper<T>
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static <T> QueryWrapper<T> initQueryWrapper(T searchObj, Map<String, String[]> parameterMap)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        long start = System.currentTimeMillis();
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        installMybatisPlus(queryWrapper, searchObj, parameterMap);
        log.debug("---查询条件构造器初始化完成,耗时:" + (System.currentTimeMillis() - start) + "毫秒----");
        return queryWrapper;
    }

    /**
     * 组装Mybatis Plus 查询条件
     * 使用此方法 需要有如下几点注意:
     * 1.使用QueryWrapper 而非LambdaQueryWrapper;
     * 2.实例化QueryWrapper时不可将实体传入参数
     * 错误示例:QueryWrapper<T> queryWrapper = new QueryWrapper<T>(t);
     * 正确示例:QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
     * 
     * @param queryWrapper
     * @param searchObj
     * @param parameterMap
     */
    private static void installMybatisPlus(QueryWrapper<?> queryWrapper, Object searchObj,
            Map<String, String[]> parameterMap) {
        // 获取查询实体对象的字段描述数组
        PropertyDescriptor[] origDescriptors = PropertyUtils.getPropertyDescriptors(searchObj);
        // 权限规则自定义SQL表达式

        // 定义实体字段和数据库字段名称的映射 高级查询中 只能获取实体字段 如果设置TableField注解 那么查询条件会出问题
        Map<String, String> fieldColumnMap = new HashMap<>(16);
        for (PropertyDescriptor origDescriptor : origDescriptors) {
            String name = origDescriptor.getName();
            String type = origDescriptor.getPropertyType().toString();
            try {
                if (isUselessField(name) || !PropertyUtils.isReadable(searchObj, name)) {
                    continue;
                }
                Object value = PropertyUtils.getSimpleProperty(searchObj, name);
                String column = getTableFieldName(searchObj.getClass(), name);
                if (column == null) {
                    // column为null只有一种情况 那就是 添加了注解@TableField(exist = false) 后续都不用处理了
                    continue;
                }
                fieldColumnMap.put(name, column);
                // 区间查询
                doIntervalQuery(queryWrapper, parameterMap, type, name, column);

                doFilterQuery(queryWrapper, parameterMap, type, name, column);
                // 判断单值 参数带不同标识字符串 走不同的查询
                boolean addQuery = addQueryByValueFlag(queryWrapper, column, value);
                if (!addQuery) {
                    if (null != value) {
                        addQueryByRule(queryWrapper, column, type, value.toString(), QueryRuleEnum.EQ);
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        // 排序逻辑 处理
        doMultiFieldsOrder(queryWrapper, parameterMap);
        // 高级查询
        doSuperQuery(queryWrapper, parameterMap, fieldColumnMap);
    }

    /**
     * 根据规则走不同的查询
     *
     * @param queryWrapper QueryWrapper
     * @param name         字段名字
     * @param rule         查询规则
     * @param value        查询条件值
     */
    private static void addEasyQuery(QueryWrapper<?> queryWrapper, String name, QueryRuleEnum rule, Object value) {
        if (value == null || rule == null || "".equals(value.toString())) {
            return;
        }
        name = StringUtils.camelToUnderline(name);
        log.info("--查询规则-->" + name + " " + rule.getValue() + " " + value);
        switch (rule) {
            case GT:
                queryWrapper.gt(name, value);
                break;
            case GE:
                queryWrapper.ge(name, value);
                break;
            case LT:
                queryWrapper.lt(name, value);
                break;
            case LE:
                queryWrapper.le(name, value);
                break;
            case EQ:
                queryWrapper.eq(name, value);
                break;
            case NE:
                queryWrapper.ne(name, value);
                break;
            case IN:
                if (value instanceof String) {
                    queryWrapper.in(name, (Object[]) value.toString().split(","));
                } else if (value instanceof String[]) {
                    queryWrapper.in(name, (Object[]) value);
                } else if (value.getClass().isArray()) {
                    queryWrapper.in(name, (Object[]) value);
                } else {
                    queryWrapper.in(name, value);
                }
                break;
            case LIKE:
                queryWrapper.like(name, value);
                break;
            case LEFT_LIKE:
                queryWrapper.likeLeft(name, value);
                break;
            case RIGHT_LIKE:
                queryWrapper.likeRight(name, value);
                break;
            default:
                log.info("--查询规则未匹配到---");
                break;
        }
    }

    /**
     * 根据规则走不同的查询,并转化值类型
     * 
     * @author Li
     * @param queryWrapper QueryWrapper
     * @param name         字段名字
     * @param type         字段类型
     * @param value        查询条件值
     * @param rule         查询规则
     * @return void
     */
    private static void addQueryByRule(QueryWrapper<?> queryWrapper, String name, String type, String value,
            QueryRuleEnum rule) throws ParseException {
        if (value != null && !"".equals(value) && !NULL.equals(value)) {
            Object temp;
            // 针对数字类型字段，多值查询
            if (value.contains(COMMA)) {
                temp = value;
                addEasyQuery(queryWrapper, name, rule, temp);
                return;
            }
            temp = covertValue(value, type, rule);
            addEasyQuery(queryWrapper, name, rule, temp);
        }
    }

    /**
     * 区间查询
     *
     * @param queryWrapper query对象
     * @param parameterMap 参数map
     * @param type         字段类型
     * @param filedName    字段名称
     * @param columnName   列名称
     */
    private static void doIntervalQuery(QueryWrapper<?> queryWrapper, Map<String, String[]> parameterMap, String type,
            String filedName, String columnName) throws ParseException {
        // 添加 判断是否有区间值
        String endValue, beginValue;
        if (parameterMap != null && parameterMap.containsKey(filedName + BEGIN)) {
            beginValue = parameterMap.get(filedName + BEGIN)[0].trim();
            addQueryByRule(queryWrapper, columnName, type, beginValue, QueryRuleEnum.GE);
        }
        if (parameterMap != null && parameterMap.containsKey(filedName + END)) {
            endValue = parameterMap.get(filedName + END)[0].trim();
            addQueryByRule(queryWrapper, columnName, type, endValue, QueryRuleEnum.LE);
        }
        // 多值查询
        if (parameterMap != null && parameterMap.containsKey(filedName + MULTI)) {
            endValue = parameterMap.get(filedName + MULTI)[0].trim();
            addQueryByRule(queryWrapper, columnName.replace(MULTI, ""), type, endValue, QueryRuleEnum.IN);
        }
    }

    /**
     * 筛选查询
     *
     * @param queryWrapper query对象
     * @param parameterMap 参数map
     * @param type         字段类型
     * @param filedName    字段名称
     * @param columnName   列名称
     */
    private static void doFilterQuery(QueryWrapper<?> queryWrapper, Map<String, String[]> parameterMap, String type,
            String filedName, String columnName) throws ParseException {
        if (parameterMap != null && parameterMap.containsKey(filedName + FILTER)) {
            String value = parameterMap.get(filedName + FILTER)[0].trim();
            if (!value.contains(COMMA)) {
                addQueryByRule(queryWrapper, columnName, type, value, QueryRuleEnum.EQ);
            } else {
                String[] arr = value.split(",");
                Object[] values = new Object[arr.length];
                for (int i = 0; i < arr.length; i++) {
                    values[i] = covertValue(arr[i], type, QueryRuleEnum.EQ);
                }
                addEasyQuery(queryWrapper, columnName, QueryRuleEnum.IN, values);
            }
        }
    }

    /**
     * 根据值中标识判断规则 走不同的查询
     *
     * @param queryWrapper 查询对象
     * @param column       字段
     * @param value        数值
     * @return boolean
     * @author Li
     */
    private static boolean addQueryByValueFlag(QueryWrapper<?> queryWrapper, String column, Object value) {
        if (value != null && !"".equals(value) && !NULL.equals(value)) {
            return false;
        }
        if (!(value instanceof String)) {
            return false;
        }
        String val = (value + "").trim();
        if (val.length() == 0) {
            return false;
        }
        QueryRuleEnum rule = QueryRuleEnum.getByValue(val.split(QUERY_SEPARATE_KEYWORD)[0]);
        if (null == rule) {
            final String field = StringUtils.camelToUnderline(column);
            // 值中含*号模糊查询，支持逗号分割的多值查询；不做逗号分割的都转IN，减少麻烦
            if (val.contains(STAR)) {
                String[] values = val.split(COMMA);
                queryWrapper.and(j -> {
                    if (values[0].startsWith(STAR) && values[0].endsWith(STAR)) {
                        j = j.like(field, values[0].substring(1, values[0].length() - 1));
                    } else if (values[0].startsWith(STAR)) {
                        j = j.likeLeft(field, values[0].substring(1));
                    } else if (values[0].endsWith(STAR)) {
                        j = j.likeRight(field, values[0].substring(0, values[0].length() - 1));
                    } else {
                        j = j.eq(field, values[0]);
                    }
                    if (values.length > 1) {
                        for (int k = 1; k < values.length; k++) {
                            if (values[k].startsWith(STAR) && values[k].endsWith(STAR)) {
                                j = j.or().like(field, values[k].substring(1, values[k].length() - 1));
                            } else if (values[k].startsWith(STAR)) {
                                j = j.or().likeLeft(field, values[k].substring(1));
                            } else if (values[k].endsWith(STAR)) {
                                j = j.or().likeRight(field, values[k].substring(0, values[k].length() - 1));
                            } else {
                                j = j.or().eq(field, values[k]);
                            }
                        }
                    }
                });
                return true;
            }
            if (NULL.equals(val)) {
                queryWrapper.isNull(field);
            }
            if (NOT_NULL.equals(val)) {
                queryWrapper.isNotNull(field);
            }
        } else {
            // 传入类型为 "flag value" 的值，由flag确定rule
            String realValue = val.substring(rule.getValue().length() + 1);
            if (rule == QueryRuleEnum.IN) {
                addEasyQuery(queryWrapper, column, rule, realValue.split(","));
            } else {
                addEasyQuery(queryWrapper, column, rule, realValue);
            }
            return true;
        }
        return false;
    }

    /**
     * 多字段排序
     * 
     * @param queryWrapper
     * @param parameterMap
     */
    private static void doMultiFieldsOrder(QueryWrapper<?> queryWrapper, Map<String, String[]> parameterMap) {
        String column = "", order = "";
        if (parameterMap != null && parameterMap.containsKey(ORDER_COLUMN)) {
            column = parameterMap.get(ORDER_COLUMN)[0];
        }
        if (parameterMap != null && parameterMap.containsKey(ORDER_TYPE)) {
            order = parameterMap.get(ORDER_TYPE)[0];
        }
        log.info("排序规则>>列:" + column + ",排序方式:" + order);
        if (isNotEmpty(column) && isNotEmpty(order)) {
            // SQL注入check
            SqlInjectionCheck.filterContent(column);
            String[] orderArray = null;
            if (order.contains(COMMA)) {
                orderArray = order.split(COMMA);
            }
            if (column.contains(COMMA)) {
                String[] columnArray = column.split(",");
                for (int i = 0; i < columnArray.length; i++) {
                    String col = columnArray[i];
                    // 字典字段，去掉字典翻译文本后缀
                    if (col.endsWith(CommonConstant.DICT_TEXT_SUFFIX)) {
                        col = col.substring(0, col.lastIndexOf(CommonConstant.DICT_TEXT_SUFFIX));
                    }
                    col = StringUtils.camelToUnderline(col);
                    String orderType;
                    if (orderArray != null && orderArray.length > 0) {
                        if (orderArray.length > i) {
                            orderType = orderArray[i];
                        } else {
                            orderType = orderArray[orderArray.length - 1];
                        }
                    } else {
                        orderType = order;
                    }
                    if (orderType.toUpperCase().indexOf(ORDER_TYPE_ASC) >= 0) {
                        queryWrapper.orderByAsc(col);
                    } else {
                        queryWrapper.orderByDesc(col);
                    }
                }
            } else {
                if (column.endsWith(CommonConstant.DICT_TEXT_SUFFIX)) {
                    column = column.substring(0, column.lastIndexOf(CommonConstant.DICT_TEXT_SUFFIX));
                }
                column = StringUtils.camelToUnderline(column);
                if (order.toUpperCase().indexOf(ORDER_TYPE_ASC) >= 0) {
                    queryWrapper.orderByAsc(column);
                } else {
                    queryWrapper.orderByDesc(column);
                }
            }

        }
    }

    /**
     * 高级查询
     *
     * @param queryWrapper   查询对象
     * @param parameterMap   参数对象
     * @param fieldColumnMap 实体字段和数据库列对应的map
     */
    private static void doSuperQuery(QueryWrapper<?> queryWrapper, Map<String, String[]> parameterMap,
            Map<String, String> fieldColumnMap) {
        if (parameterMap != null && parameterMap.containsKey(SUPER_QUERY_PARAMS)) {
            String superQueryParams = parameterMap.get(SUPER_QUERY_PARAMS)[0];
            // 高级查询的条件要用括号括起来，防止和用户的其他条件冲突 -------
            try {
                superQueryParams = URLDecoder.decode(superQueryParams, "UTF-8");
                List<QueryCondition> conditions = JSON.parseArray(superQueryParams, QueryCondition.class);
                if (conditions == null || conditions.size() == 0) {
                    return;
                }
                log.info("---高级查询参数-->" + conditions.toString());

                queryWrapper.and(andWrapper -> {
                    for (int i = 0; i < conditions.size(); i++) {
                        QueryCondition rule = conditions.get(i);
                        if (isNotEmpty(rule.getField())
                                && isNotEmpty(rule.getRule())
                                && isNotEmpty(rule.getValue())) {
                            log.debug("SuperQuery ==> " + rule.toString());
                            // 【高级查询】 oracle 日期等于查询报错
                            Object queryValue = rule.getValue();
                            if ("localDate".equals(rule.getType())) {
                                queryValue = DateUtil.str2LocalDate(rule.getValue(), DateUtil.DATETIME_FORMAT);
                            } else if ("localDatetime".equals(rule.getType())) {
                                queryValue = DateUtil.str2LocalDateTime(rule.getValue(), DateUtil.DATETIME_FORMAT);
                            } else if ("date".equals(rule.getType())) {
                                queryValue = DateUtil.str2Date(rule.getValue(), DateUtil.SHORT_DATE_FORMAT);
                            } else if ("datetime".equals(rule.getType())) {
                                queryValue = DateUtil.str2Date(rule.getValue(), DateUtil.DATETIME_FORMAT);
                            }
                            addEasyQuery(andWrapper, fieldColumnMap.get(rule.getField()),
                                    QueryRuleEnum.getByValue(rule.getRule()), queryValue);
                            // 如果拼接方式是OR，就拼接OR
                            if ("or".equals(conditions.get(i).getRule()) && i < (conditions.size() - 1)) {
                                andWrapper.or();
                            }
                        }
                    }
                    // return andWrapper;
                });
            } catch (UnsupportedEncodingException e) {
                log.error("--高级查询参数转码失败:" + superQueryParams, e);
            } catch (Exception e) {
                log.error("--高级查询拼接失败:" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * 数值转换
     * 
     * @param value 数值
     * @param type  类型
     * @param rule  规则
     * @return
     * @throws ParseException
     */
    private static Object covertValue(String value, String type, QueryRuleEnum rule) throws ParseException {
        Object temp;
        switch (type) {
            case "class java.lang.Integer":
                temp = Integer.parseInt(value);
                break;
            case "class java.math.BigDecimal":
                temp = new BigDecimal(value);
                break;
            case "class java.lang.Short":
                temp = Short.parseShort(value);
                break;
            case "class java.lang.Long":
                temp = Long.parseLong(value);
                break;
            case "class java.lang.Float":
                temp = Float.parseFloat(value);
                break;
            case "class java.lang.Double":
                temp = Double.parseDouble(value);
                break;
            case "class java.time.LocalDateTime":
                temp = getLocalDateTimeQueryByRule(value, rule);
                break;
            case "class java.util.Date":
                temp = getDateQueryByRule(value, rule);
                break;
            default:
                temp = value;
                break;
        }
        return temp;
    }

    /**
     * 判断是否非空
     * 
     * @param value
     * @return
     */
    private static boolean isNotEmpty(Object value) {
        return value != null && !"null".equals(value.toString().trim().toLowerCase()) && !"".equals(value.toString());
    }

    /**
     * 判断是否为无用字段
     * 
     * @param name 字段名
     * @return
     */
    private static boolean isUselessField(String name) {
        return "class".equals(name) || "ids".equals(name)
                || "page".equals(name) || "rows".equals(name)
                || "sort".equals(name) || "order".equals(name);
    }

    /**
     * 获取实体类字段列表
     * 
     * @param clazz 类类型
     * @return
     */
    private static List<Field> getClassFields(Class<?> clazz) {
        List<Field> list = new ArrayList<>();
        Field[] fields;
        do {
            fields = clazz.getDeclaredFields();
            Collections.addAll(list, fields);
            clazz = clazz.getSuperclass();
        } while (clazz != Object.class && clazz != null);
        return list;
    }

    /**
     * 获取数据库表中对应的字段名
     * 
     * @param clazz 实体类类型
     * @param name  字段名
     * @return
     */
    private static String getTableFieldName(Class<?> clazz, String name) {
        try {
            // 如果字段加注解了@TableField(exist = false),不走DB查询
            Field field = null;
            try {
                field = clazz.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                List<Field> allFields = getClassFields(clazz);
                List<Field> searchFields = allFields.stream().filter(a -> a.getName().equals(name))
                        .collect(Collectors.toList());
                if (searchFields.size() > 0) {
                    field = searchFields.get(0);
                }
            }
            if (field != null) {
                TableField tableField = field.getAnnotation(TableField.class);
                if (tableField != null) {
                    if (!tableField.exist()) {
                        // 如果设置了TableField false 这个字段不需要处理
                        return null;
                    } else {
                        String column = tableField.value();
                        // 如果设置了TableField value 这个字段是实体字段
                        if (!"".equals(column)) {
                            return column;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * 获取日期类型的值
     *
     * @param value 值
     * @param rule  规则
     * @return java.util.Date
     * @author Li
     */
    private static Date getDateQueryByRule(String value, QueryRuleEnum rule) throws ParseException {
        LocalDateTime localDateTime = getLocalDateTimeQueryByRule(value, rule);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取日期类型的值
     * 
     * @param value 值
     * @param rule  规则
     * @return LocalDateTime
     * @throws ParseException
     */
    private static LocalDateTime getLocalDateTimeQueryByRule(String value, QueryRuleEnum rule) throws ParseException {
        DateTimeFormatter dateTimeFormatter = DateUtil.DATETIME_FORMAT;
        LocalDateTime localDateTime = null;
        if (value.length() == DATE_LENGTH) {
            if (rule == QueryRuleEnum.GE) {
                localDateTime = LocalDateTime.parse(value + " 00:00:00", dateTimeFormatter);
            } else if (rule == QueryRuleEnum.LE) {
                localDateTime = LocalDateTime.parse(value + " 23:59:59", dateTimeFormatter);
            }
        }
        if (localDateTime == null) {
            localDateTime = LocalDateTime.parse(value, dateTimeFormatter);
        }
        return localDateTime;
    }

}
