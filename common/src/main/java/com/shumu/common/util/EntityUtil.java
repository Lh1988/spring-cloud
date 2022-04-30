package com.shumu.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
* @Description: 实体类处理工具
* @Author: Li
* @Date: 2022-01-11
* @LastEditTime: 2022-01-11
* @LastEditors: Li
*/
public class EntityUtil {
    public static Field[] getEntityClassFields(Class<?> entityClass) {
        List<Field> list = new ArrayList<>();
        Field[] fields;
        do {
            fields = entityClass.getDeclaredFields();
            Collections.addAll(list, fields);
            entityClass = entityClass.getSuperclass();
        } while (entityClass != Object.class && entityClass != null);
        return list.toArray(fields);
    }
}
