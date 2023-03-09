package com.shumu.data.db.create.constant;
/**
* @description: 
* @author: Li
* @date: 2023-02-09
*/
public interface CreateTableConstant {
    public static final Integer TABLE_ADDED= 0;
    public static final Integer TABLE_CREATED = 1;
    public static final Integer TABLE_CHANGED = 2;

    public static final Integer FIELD_ADDED= 0;
    public static final Integer FIELD_CREATED = 1;
    public static final Integer FIELD_CHANGED = 2;
    public static final Integer FIELD_DELETED = 3;
    
    public static final Integer INDEX_ADDED= 0;
    public static final Integer INDEX_CREATED = 1;
    public static final Integer INDEX_CHANGED = 2;
    public static final Integer INDEX_DELETED = 3;

    public static final Integer TYPE_NONE= 0;
    public static final Integer TYPE_AUTO = 1;
    public static final Integer TYPE_INPUT = 2;
}
