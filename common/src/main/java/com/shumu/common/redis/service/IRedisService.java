package com.shumu.common.redis.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* @Description: 
* @Author: Li
* @Date: 2022-02-07
* @LastEditTime: 2022-02-07
* @LastEditors: Li
*/
public interface IRedisService {
    /**
     * 设置过期时间
     * @param key
     * @param time
     * @return
     */
    boolean setExpire(String key, long time);
    /**
     * 获取过期时间
     * @param key
     * @return
     */
    long getExpire(String key);
    /**
     * 是否含有目标key
     * @param key
     * @return
     */
    boolean hasKey(String key);
    /**
     * 删除
     * @param key
     */
    void delete(String... key);
    /**
     * 获取字符串值
     * @param key
     * @return
     */
    String getString(String key);
    /**
     * 设置字符串值
     * @param key
     * @param value
     * @return
     */
    boolean setString(String key, String value);
    /**
     * 设置字符串值与过期时间
     * @param key
     * @param value
     * @param time
     * @return
     */
    boolean setString(String key, String value, long time);
    /**
     * 获取对象值
     * @param key
     * @return
     */
    Object getObject(String key);
    /**
     * 设置对象值
     * @param key
     * @param value
     * @return
     */
    boolean setObject(String key, Object value);
    /**
     * 设置对象值与缓存时长
     * @param key
     * @param value
     * @param time
     * @return
     */
    boolean setObject(String key, Object value, long time);
    /**
     * 以增量的方式将long值存储在变量中
     * @param key
     * @param delta
     * @return
     */
    long increment(String key, long delta); 
    /**
     * 以增量的方式将double值存储在变量中
     * @param key
     * @param delta
     * @return
     */
   double increment(String key, double delta); 
    /**
     * 获取哈希表中对象
     * @param key redis存储键值
     * @param item hash表中键值
     * @return
     */
    Object getHashItem(String key, String item);
    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key redis存储键值
     * @param item hash表中键值
     * @param value
     * @return
     */
    boolean setHashItem(String key, String item, Object value);
    /**
     * 设置哈希表选项与缓存时长
     * @param key redis存储键值
     * @param item hash表中键值
     * @param value
     * @param time
     * @return
     */
    boolean setHashItem(String key, String item, Object value, long time);
    /**
     * 删除hash表中项
     * @param key
     * @param item
     */
    void removeHashItem(String key, Object... item);
    /**
     * hash表中是否含有键值item
     * @param key
     * @param item
     * @return
     */
    boolean hashHasKey(String key, String item);
    /**
     * 获取hashKey对应的所有键值
     * @param key
     * @return
     */
    Map<Object, Object> getHashMap(String key);
    /**
     * 存储HashMap
     * @param key
     * @param value
     * @return
     */
    boolean setHashMap(String key, Map<String, Object> value);
    /**
     * 存储HashMap并设置缓存时长
     * @param key
     * @param value
     * @param time
     * @return
     */
    boolean setHashMap(String key, Map<String, Object> value, long time);
    /**
     * 获取List对象
     * @param key   键
	 * @param start 开始
	 * @param end   结束 0 到 -1代表所有值
     * @return
     */
    List<Object> getList(String key, long start, long end);
    /**
     * 获取list缓存的长度
     * @param key
     * @return
     */
    long getListSize(String key);
    /**
     * 获取List选项
     * @param key
     * @param index
     * @return
     */
    Object getListItem(String key, long index);
    /**
     * 存储list
     * @param key
     * @param value
     * @return
     */
    boolean setList(String key, List<Object> value);
    /**
     * 存储list
     * @param key
     * @param value
     * @param time
     * @return
     */
    boolean setList(String key, List<Object> value, long time);
    /**
     * 向list添加选项
     * @param key
     * @param value
     * @return
     */
    boolean addList(String key, Object value);
    /**
     * 向list添加选项
     * @param key
     * @param value
     * @param time
     * @return
     */
    boolean addList(String key, Object value, long time);
    /**
     * 修改list的选项
     * @param key
     * @param index
     * @param value
     * @return
     */
    boolean updateListItem(String key, long index, Object value);
    /**
     * 移除list中值为value的count个选项
     * @param key
     * @param count
     * @param value
     * @return
     */
    long removeListItem(String key, long count, Object value);
    /**
     * 根据key获取Set中的所有值
     * @param key
     * @return
     */
    Set<Object> getSet(String key);
    /**
     * 存储set
     * @param key
     * @param values
     * @return
     */
    long setSet(String key, Object... values);
    /**
     * 存储set
     * @param key
     * @param time
     * @param values
     * @return
     */
    long setSet(String key, long time, Object... values);
}
