package com.shumu.common.query.enums;
/**
* @Description: Query Rule Enum
* @Author: Li
* @Date: 2021-12-30
* @LastEditTime: 2021-12-30
* @LastEditors: Li
*/
public enum QueryRuleEnum {
    /**great then */
    GT(">", "gt", "大于"),
    /**great equal */
    GE(">=", "ge", "大于等于"),
    /**little then */
    LT("<", "lt", "小于"),
    /**little then */
    LE("<=", "le", "小于等于"),
    /**equal */
    EQ("=", "eq", "等于"),
    /**not equal */
    NE("!=", "ne", "不等于"),
    /**in */
    IN("IN", "in", "包含"),
    /**like */
    LIKE("LIKE", "like", "全模糊"),
    /**left like */
    LEFT_LIKE("LEFT_LIKE", "left_like", "左模糊"),
    /**right like */
    RIGHT_LIKE("RIGHT_LIKE", "right_like", "右模糊"),
    /**ext */
    SQL_RULES("USE_SQL_RULES", "ext", "自定义SQL片段");

    private String value;

    private String condition;

    private String msg;

    QueryRuleEnum(String value, String condition, String msg) {
        this.value = value;
        this.condition = condition;
        this.msg = msg;
    }

    public static QueryRuleEnum getByValue(String value) {
        if (null==value || "".equals(value)) {
            return null;
        }
        for (QueryRuleEnum val : values()) {
            if (val.getValue().equals(value) || val.getCondition().equals(value)) {
                return val;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
