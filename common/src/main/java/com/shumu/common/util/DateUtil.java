package com.shumu.common.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
/**
* @Description: 时间日期转换工作
* @Author: Li
* @Date: 2022-01-05
* @LastEditTime: 2022-01-05
* @LastEditors: Li
*/
public class DateUtil {
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter SHORT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final int DATE_LENGTH = 10;
    /**
     * String to LocalDateTime
     * @param str
     * @param formatter
     * @return
     */
    public static LocalDateTime str2LocalDateTime(String str, DateTimeFormatter formatter) {
        if(str.length() <= DATE_LENGTH){
            return LocalDate.parse(str,formatter).atStartOfDay();
        }
        return LocalDateTime.parse(str,formatter);
    }
    /**
     * String to LocalDateTime
     * @param str
     * @return
     */
    public static LocalDateTime str2LocalDateTime(String str) {
        return str2LocalDateTime(str,DATETIME_FORMAT);
    }
    /**
     * String to LocalDate
     * @param str
     * @param formatter
     * @return
     */
    public static LocalDate str2LocalDate(String str, DateTimeFormatter formatter) {
        return LocalDate.parse(str,formatter);
    }
    /**
     * String to LocalDate
     * @param str
     * @return
     */
    public static LocalDate str2LocalDate(String str) {
        return str2LocalDate(str,SHORT_DATE_FORMAT);
    }
    /**
     * String to Date
     * @param str
     * @param formatter
     * @return
     */
    public static Date str2Date(String str, DateTimeFormatter formatter) {
        return toDate(LocalDateTime.parse(str, formatter));
    }
    /**
     * String to Date
     * @param str
     * @return
     */
    public static Date str2Date(String str) {
        return str2Date(str,DATETIME_FORMAT);
    }

    /**
     * LocalDate to Date
     * @param localDate
     * @return
     */
    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    /**
     * LocalDateTime to Date
     * @param localDateTime
     * @return
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
    /**
     * Date to LocalDate
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }
    /**
     * Date to LocalDateTime
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static String localDateTime2Str(LocalDateTime localDateTime,DateTimeFormatter formatter){
        return localDateTime.format(formatter);
    }

    public static String localDate2Str(LocalDate localDate,DateTimeFormatter formatter){
        return localDate.format(formatter);
    }

    public static String date2Str(Date date,DateTimeFormatter formatter){
        return toLocalDateTime(date).format(formatter);
    }
}
