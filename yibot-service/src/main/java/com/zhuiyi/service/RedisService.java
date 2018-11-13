package com.zhuiyi.service;

import com.zhuiyi.config.RedisLock;
import com.zhuiyi.model.dto.FaqInfoDTO;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author tree
 * @version 1.0
 * date: 2018/7/30 15:19
 * description: Redis操作服务类
 * own: zhuiyi
 */
public interface RedisService {

    /**
     * 将key/value值保存到redis中
     *
     * @param key   键
     * @param value 值
     * @return void
     */
    void save(String key, Object value);

    /**
     * 将key/value值保存到redis中,并根据条件指定过期的时间长度
     *
     * @param key      键
     * @param num      时间长度
     * @param value    值
     * @param timeUnit 时间单位
     * @return void
     */
    void saveAndExist(String key, Object value, Long num, TimeUnit timeUnit);

    /**
     * 将key/value值保存到redis中,并指定过期的日期
     *
     * @param key        键
     * @param value      值
     * @param expireDate 过期的日期
     * @return void
     */
    void save(String key, Object value, Date expireDate);

    /**
     * 将key/value值保存到redis中,并指定过期的时间长度
     *
     * @param key      键
     * @param value    值
     * @param num      时间长度
     * @param timeUnit 时间单位
     * @return void
     */
    void save(String key, Object value, Long num, TimeUnit timeUnit);

    /**
     * 指定Key过期的日期
     *
     * @param key        键
     * @param expireDate 过期的日期
     * @return void
     */
    void setExpireAt(String key, Date expireDate);

    /**
     * 指定Key过期的时间长度
     *
     * @param key      键
     * @param num      时间长度
     * @param timeUnit 时间单位
     * @return void
     */
    void setExpire(String key, Long num, TimeUnit timeUnit);

    /**
     * 将多个key/value值保存到redis中,并指定过期的日期
     *
     * @param mapData    键值MAP
     * @param expireDate 过期的日期
     * @return void
     */
    void multiSave(Map<String, Object> mapData, Date expireDate);

    /**
     * 查找对应key的值
     *
     * @param key 键
     * @return Object 值
     */
    Object find(String key);

    /**
     * 根据键集合批量获取值
     *
     * @param keys 键集合
     * @return List<Object> 值的集合
     */
    List<Object> multiFind(List<String> keys);

    /**
     * 对指定的key进行计数
     *
     * @param key   键
     * @param delta 计数值
     * @return Long  返回计数后的值
     */
    Long increment(String key, Long delta);

    /**
     * 对指定的key进行计数,并根据条件指定过期的时间长度
     *
     * @param key      键
     * @param delta    计数值
     * @param num      时间长度
     * @param timeUnit 时间单位
     * @return Long  返回计数后的值
     */
    Long incrementAndExist(String key, Long delta, Long num, TimeUnit timeUnit);

    /**
     * 获取指定的key的操作锁,并根据条件指定过期的时间和超时时间
     *
     * @param key      键
     * @param timeoutMsecs    获取锁的超时时间
     * @param expireMsecs 锁的有效期
     * @return RedisLock  Redis 分布式锁
     */
    Optional<RedisLock> getLock(String key, int timeoutMsecs, int expireMsecs);

    /**
     * 判断value是否在键为key的Set中
     *
     * @param key   键
     * @param value 值
     * @return boolean 是否在set中
     */
    boolean isMemberOfSet(String key, Object value);

    /**
     * 将key/value值保存到redis的set数据结构中，并根据条件指定过期的时间长度
     *
     * @param key      键
     * @param value    值
     * @param num      时间长度
     * @param timeUnit 时间单位
     * @return void
     */
    void addSetAndExist(String key, Object value, Long num, TimeUnit timeUnit);

    /**
     * 根据key获取set中的所有元素
     *
     * @param key      键
     * @return void
     */
    Set<String> findSetMembers(String key);

    /**
     * 将key/value值保存到redis的set数据结构中，并根据条件指定过期的时间长度
     *
     * @param key      键
     * @param value    值
     * @param score    分值
     * @param num      时间长度
     * @param timeUnit 时间单位
     * @return void
     */
    void addZSetAndExist(String key, Object value, double score, Long num, TimeUnit timeUnit);

    /**
     * 查找指定区间的Zset数据
     *
     * @param key   键
     * @param start 开始区间
     * @param end   结束区间
     * @return List<ZSetOperations.TypedTuple < Object>> 指定区间的Zset数据
     */
    List<ZSetOperations.TypedTuple<Object>> findZSetReverseRangeWithScores(String key, long start, long end);

    /**
     * 查找Zset数据总量
     *
     * @param key 键
     * @param value 值
     * @return Double 返回分数
     */
    Double findZSetScore(String key,Object value);

    /**
     * 查找Zset数据总量
     *
     * @param key 键
     * @return Long Zset数据总量
     */
    Long countZSet(String key);

    /**
     * 按条件查找Zset
     *
     * @param key 键
     * @param pattern 查询条件
     * @param num 数量条件
     * @return List<FaqInfoDTO> Zset数据列表
     */
     List<FaqInfoDTO> scanZSet(String key, String pattern, int num);

    /**
     * 将key/value值保存到redis的Hash数据结构中，并根据条件指定过期的时间长度
     *
     * @param hashKey  hash键名
     * @param key      键
     * @param value    值
     * @param num      时间长度
     * @param timeUnit 时间单位
     * @return void
     */
    void addHashAndExist(String hashKey, String key, Object value, Long num, TimeUnit timeUnit);

    /**
     * 获取Hash键名获取值
     *
     * @param hashKey hash键名
     * @param key 键
     * @return Object 值
     */
    Object findHash(String hashKey, String key);

    /**
     * 获取集合键名获取值的列表
     *
     * @param hashKey hash键名
     * @return List<Object> 值列表
     */
    List<Object> findHashAll(String hashKey);

    /**
     * 将key/value值保存到redis的List数据结构中
     *
     * @param key   键
     * @param value 值
     * @return void
     */
    void addList(String key, Object value);

    /**
     * 将key/value值保存到redis的Hash数据结构中,并指定过期的日期
     *
     * @param hashKey    hash键名
     * @param key        键
     * @param value      值
     * @param expireDate 过期的日期
     * @return void
     */
    void addHash(String hashKey, String key, Object value, Date expireDate);

    /**
     * 将key/value值保存到redis的Hash数据结构中
     *
     * @param hashKey  hash键名
     * @param key      键
     * @param value    值
     * @param num      时间长度
     * @param timeUnit 时间单位
     * @return void
     */
    void addHash(String hashKey, String key, Object value, Long num, TimeUnit timeUnit);

    /**
     * 将key/value值保存到redis的Hash数据结构中
     *
     * @param hashKey hash键名
     * @param key     键
     * @param value   值
     * @return void
     */
    void addHash(String hashKey, String key, Object value);

    /**
     * 将key/value值保存到redis的List数据结构中
     *
     * @param key      键
     * @param value    值
     * @param num      时间长度
     * @param timeUnit 时间单位
     * @return void
     */
    void addList(String key, Object value, Long num, TimeUnit timeUnit);

    /**
     * 获取集合键值获取值列表
     *
     * @param key 集合键值
     * @return List<Object> 值列表
     */
    List<Object> findAll(String key);
}
