package com.shumu.data.database.constant;
/**
* @description: Database Constant
* @author: Li
* @date: 2023-02-06
*/
public interface DatabaseConstant {
    public static final Integer CREATE_BY_INFO = 1;
    public static final Integer CREATE_BY_REFERENCED = 2;
    public static final Integer CREATE_TABLE_VIEW = 3;

    public static final Integer TABLE_INFO_CREATED= 0;
    public static final Integer TABLE_INFO_UPDATED = 2;
    public static final Integer DB_TABLE_CREATED = 1;

    public static final Integer FIELD_INFO_CREATED= 0;
    public static final Integer FIELD_INFO_UPDATED = 2;
    public static final Integer FIELD_INFO_DELETED = 3;

    public static final Integer INDEX_INFO_CREATED= 0;
    public static final Integer INDEX_INFO_UPDATED = 2;
    public static final Integer INDEX_INFO_DELETED = 3;
}
