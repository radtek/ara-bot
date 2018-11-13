package com.zhuiyi.service.impl;

import com.zhuiyi.config.RedisLock;
import com.zhuiyi.model.dto.FaqInfoDTO;
import com.zhuiyi.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author tree
 * @version 1.0
 * date: 2018/7/30 15:21
 * description: Redis操作服务实现类
 * own: zhuiyi
 */
@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 将key/value值保存到redis中
     *
     * @param key   键
     * @param value 值
     * @return void
     */
    @Override
    public void save(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 将key/value值保存到redis中,并根据条件指定过期的时间长度
     *
     * @param key      键
     * @param value    值
     * @param num      时间长度
     * @param timeUnit 时间单位
     * @return void
     */
    @Override
    public void saveAndExist(String key, Object value, Long num, TimeUnit timeUnit) {
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            redisTemplate.opsForValue().getOperations().expire(key, num, timeUnit);
        } else {
            long expire = redisTemplate.opsForValue().getOperations().getExpire(key, timeUnit);
            redisTemplate.opsForValue().set(key, value);
            redisTemplate.opsForValue().getOperations().expire(key, expire, timeUnit);
        }
    }

    /**
     * 将key/value值保存到redis中,并指定过期的日期
     *
     * @param key        键
     * @param value      值
     * @param expireDate 过期的日期
     * @return void
     */
    @Override
    public void save(String key, Object value, Date expireDate) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.opsForValue().getOperations().expireAt(key, expireDate);
    }

    /**
     * 将key/value值保存到redis中,并指定过期的时间长度
     *
     * @param key      键
     * @param value    值
     * @param num      时间长度
     * @param timeUnit 时间单位
     * @return void
     */
    @Override
    public void save(String key, Object value, Long num, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, num, timeUnit);
    }

    /**
     * 指定Key过期的日期
     *
     * @param key        键
     * @param expireDate 过期的日期
     * @return void
     */
    @Override
    public void setExpireAt(String key, Date expireDate) {
        redisTemplate.opsForValue().getOperations().expireAt(key, expireDate);
    }

    /**
     * 指定Key过期的时间长度
     *
     * @param key      键
     * @param num      时间长度
     * @param timeUnit 时间单位
     * @return void
     */
    @Override
    public void setExpire(String key, Long num, TimeUnit timeUnit) {
        redisTemplate.opsForValue().getOperations().expire(key, num, timeUnit);
    }

    /**
     * 将多个key/value值保存到redis中,并指定过期的日期
     *
     * @param mapData    键值MAP
     * @param expireDate 过期的日期
     * @return void
     */
    @Override
    public void multiSave(Map<String, Object> mapData, Date expireDate) {
        redisTemplate.opsForValue().multiSet(mapData);
        mapData.entrySet().forEach(x -> {
            redisTemplate.opsForValue().getOperations().expireAt(x.getKey(), expireDate);
        });
    }

    /**
     * 查找对应key的值
     *
     * @param key 键
     * @return Object 值
     */
    @Override
    public Object find(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 根据键集合批量获取值
     *
     * @param keys 键集合
     * @return List<Object> 值的集合
     */
    @Override
    public List<Object> multiFind(List<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 对指定的key进行计数
     *
     * @param key   键
     * @param delta 计数值
     * @return Long  返回计数后的值
     */
    @Override
    public Long increment(String key, Long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 对指定的key进行计数,并根据条件指定过期的时间长度
     *
     * @param key      键
     * @param delta    计数值
     * @param num      时间长度
     * @param timeUnit 时间单位
     * @return Long  返回计数后的值
     */
    @Override
    public Long incrementAndExist(String key, Long delta, Long num, TimeUnit timeUnit) {
        Long resultTemp = redisTemplate.opsForValue().increment(key, delta);
        if (1L == resultTemp) {
            redisTemplate.opsForValue().getOperations().expire(key, num, timeUnit);
        }
        return resultTemp;
    }

    /**
     * 获取指定的key的操作锁,并根据条件指定过期的时间和超时时间
     *
     * @param key          键
     * @param timeoutMsecs 获取锁的超时时间
     * @param expireMsecs  锁的有效期
     * @return RedisLock  Redis 分布式锁
     */
    @Override
    public Optional<RedisLock> getLock(String key, int timeoutMsecs, int expireMsecs) {
        Optional<RedisLock> redisLock = Optional.ofNullable(new RedisLock(redisTemplate, key, timeoutMsecs, expireMsecs));
        try {
            if (redisLock.isPresent() && redisLock.get().lock()) {
                return redisLock;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(null);
    }

    /**
     * 判断value是否在键为key的Set中
     *
     * @param key   键
     * @param value 值
     * @return boolean 是否在set中
     */
    @Override
    public boolean isMemberOfSet(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 将key/value值保存到redis的set数据结构中，并根据条件指定过期的时间长度
     *
     * @param key      键
     * @param value    值
     * @param num      时间长度
     * @param timeUnit 时间单位
     * @return void
     */
    @Override
    public void addSetAndExist(String key, Object value, Long num, TimeUnit timeUnit) {
        if (redisTemplate.opsForSet().size(key) > 0) {
            redisTemplate.opsForSet().add(key, value);
        } else {
            redisTemplate.opsForSet().add(key, value);
            if(num > 0){
                redisTemplate.opsForSet().getOperations().expire(key, num, timeUnit);
            }
        }
    }

    /**
     * 根据key获取set中的所有元素
     *
     * @param key 键
     * @return void
     */
    @Override
    public Set<String> findSetMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

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
    @Override
    public void addZSetAndExist(String key, Object value, double score, Long num, TimeUnit timeUnit) {
        if (redisTemplate.opsForZSet().size(key) > 0) {
            redisTemplate.opsForZSet().incrementScore(key, value, score);
        } else {
            redisTemplate.opsForZSet().add(key, value, score);
            redisTemplate.opsForSet().getOperations().expire(key, num, timeUnit);
        }
    }

    /**
     * 查找指定区间的Zset数据
     *
     * @param key   键
     * @param start 开始区间
     * @param end   结束区间
     * @return List<ZSetOperations.TypedTuple   <   Object>> 指定区间的Zset数据
     */
    @Override
    public List<ZSetOperations.TypedTuple<Object>> findZSetReverseRangeWithScores(String key, long start, long end) {
        return (List<ZSetOperations.TypedTuple<Object>>) redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end).stream().collect(Collectors.toList());
    }

    /**
     * 查找Zset数据总量
     *
     * @param key   键
     * @param value 值
     * @return Double 返回分数
     */
    @Override
    public Double findZSetScore(String key, Object value) {
        //第三包里面如果指定的key找不到，会抛出NPM，此处做处理
        double n;
        try {
            n = redisTemplate.opsForZSet().score(key, value);
        } catch (NullPointerException e) {
            n = 0D;
        }
        return n;
    }

    /**
     * 查找Zset数据总量
     *
     * @param key 键
     * @return Long Zset数据总量
     */
    @Override
    public Long countZSet(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 按条件查找Zset
     *
     * @param key     键
     * @param pattern 查询条件
     * @param num     数量条件
     * @return Long Zset数据总量
     */
    @Override
    public List<FaqInfoDTO> scanZSet(String key, String pattern, int num) {
        List<FaqInfoDTO> listTemp = new ArrayList<>();
        ScanOptions scanOptions;
        if (0 == num) {
            scanOptions = ScanOptions.scanOptions().match(pattern).build();
        } else {
            scanOptions = ScanOptions.scanOptions().match(pattern).count(num).build();
        }
        Cursor<ZSetOperations.TypedTuple<Object>> cursor = redisTemplate.opsForZSet().scan(key, scanOptions);
        while (cursor.hasNext()) {
            ZSetOperations.TypedTuple<Object> item = cursor.next();
            FaqInfoDTO faqInfoDTO = new FaqInfoDTO();
            faqInfoDTO.setKey((String) item.getValue());
            faqInfoDTO.setValue(Math.round(item.getScore()));
            listTemp.add(faqInfoDTO);
        }
        return listTemp;
    }

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
    @Override
    public void addHashAndExist(String hashKey, String key, Object value, Long num, TimeUnit timeUnit) {
        if (redisTemplate.opsForHash().size(hashKey) > 0) {
            redisTemplate.opsForHash().put(hashKey, key, value);
        } else {
            redisTemplate.opsForHash().put(hashKey, key, value);
            if(num > 0){
                redisTemplate.opsForHash().getOperations().expire(hashKey, num, timeUnit);
            }
        }
    }

    /**
     * 获取Hash键名获取值
     *
     * @param hashKey hash键名
     * @param key     键
     * @return Object 值
     */
    @Override
    public Object findHash(String hashKey, String key) {
        return redisTemplate.opsForHash().get(hashKey, key);
    }

    /**
     * 获取集合键名获取值的列表
     *
     * @param hashKey hash键名
     * @return List<Object> 值列表
     */
    @Override
    public List<Object> findHashAll(String hashKey) {
        return redisTemplate.opsForHash().values(hashKey);
    }

    /**
     * 将key/value值保存到redis的List数据结构中
     *
     * @param key   键
     * @param value 值
     * @return void
     */
    @Override
    public void addList(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 将key/value值保存到redis的Hash数据结构中,并指定过期的日期
     *
     * @param hashKey    hash键名
     * @param key        键
     * @param value      值
     * @param expireDate 过期的日期
     * @return void
     */
    @Override
    public void addHash(String hashKey, String key, Object value, Date expireDate) {
        redisTemplate.opsForHash().put(hashKey, key, value);
        redisTemplate.opsForHash().getOperations().expireAt(hashKey, expireDate);
    }

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
    @Override
    public void addHash(String hashKey, String key, Object value, Long num, TimeUnit timeUnit) {
        redisTemplate.opsForHash().put(hashKey, key, value);
        redisTemplate.opsForHash().getOperations().expire(hashKey, num, timeUnit);
    }

    /**
     * 将key/value值保存到redis的Hash数据结构中
     *
     * @param hashKey hash键名
     * @param key     键
     * @param value   值
     * @return void
     */
    @Override
    public void addHash(String hashKey, String key, Object value) {
        redisTemplate.opsForHash().put(hashKey, key, value);
    }

    /**
     * 将key/value值保存到redis的List数据结构中
     *
     * @param key      键
     * @param value    值
     * @param num      时间长度
     * @param timeUnit 时间单位
     * @return void
     */
    @Override
    public void addList(String key, Object value, Long num, TimeUnit timeUnit) {
        redisTemplate.opsForList().leftPush(key, value);
        redisTemplate.opsForList().getOperations().expire(key, num, timeUnit);
    }

    /**
     * 获取集合键值获取值列表
     *
     * @param key 集合键值
     * @return List<Object> 值列表
     */
    @Override
    public List<Object> findAll(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }
}
