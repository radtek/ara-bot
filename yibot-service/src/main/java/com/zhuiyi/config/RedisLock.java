package com.zhuiyi.config;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author tree
 * @version 1.0
 * date: 2018/8/9 19:31
 * description: Redis分布式锁实现
 * own:
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class RedisLock {

    private RedisTemplate<String, String> redisTemplate;
    /**
     * 重试时间
     */
    private static final int DEFAULT_ACQUIRY_RETRY_MILLIS = 100;
    /**
     * 锁的前缀
     */
    private static final String LOCK_PREFIX = "yibot:";
    /**
     * 锁的后缀
     */
    private static final String LOCK_SUFFIX = ":redis:lock";
    /**
     * 锁的key
     */
    private String lockKey;
    /**
     * 锁超时时间，防止线程在入锁以后，防止阻塞后面的线程无法获取锁
     */
    private int expireMsecs = 60 * 1000;
    /**
     * 线程获取锁的等待时间
     */
    private int timeoutMsecs = 10 * 1000;
    /**
     * 是否锁定标志
     */
    private volatile boolean locked = false;

    /**
     * 构造器
     *
     * @param redisTemplate redis模板操作类
     * @param lockKey 锁的key
     */
    public RedisLock(RedisTemplate<String, String> redisTemplate, String lockKey){
        this.redisTemplate = redisTemplate;
        this.lockKey = LOCK_PREFIX + lockKey + LOCK_SUFFIX;
    }

    /**
     * 构造器
     *
     * @param redisTemplate redis模板操作类
     * @param lockKey 锁的key
     * @param timeoutMsecs 获取锁的超时时间
     */
    public RedisLock(RedisTemplate<String, String> redisTemplate, String lockKey, int timeoutMsecs){
        this(redisTemplate, lockKey);
        this.timeoutMsecs = timeoutMsecs;
    }

    /**
     * 构造器
     *
     * @param redisTemplate redis模板操作类
     * @param lockKey 锁的key
     * @param timeoutMsecs 获取锁的超时时间
     * @param expireMsecs 锁的有效期
     */
    public RedisLock(RedisTemplate<String, String> redisTemplate, String lockKey, int timeoutMsecs, int expireMsecs){
        this(redisTemplate, lockKey, timeoutMsecs);
        this.expireMsecs = expireMsecs;
    }

    public String getLockKey() {
        return lockKey;
    }

    /**
     * 封装和jedis方法
     *
     * @param key 键
     * @return String 获取值
     */
    private String get(final String key) {
        Object obj;
        try {
            obj = redisTemplate.opsForValue().get(key);
            return obj.toString();
        } catch (NullPointerException ex) {
            return null;
        }
    }

    /**
     * 封装和jedis方法
     *
     * @param key 键
     * @param value 值
     * @return boolean 设置是否成功
     */
    private boolean setNX(final String key, final String value) {
        boolean resFlag =  redisTemplate.opsForValue().setIfAbsent(key, value);
        //默认设置一个小时过期
        redisTemplate.opsForValue().getOperations().expire(key, expireMsecs * 6, TimeUnit.MILLISECONDS);
        return resFlag;
    }

    /**
     * 封装和jedis方法
     *
     * @param key 键
     * @param value 值
     * @return String 获取旧值
     */
    private String getSet(final String key, final String value) {
        Object obj = redisTemplate.opsForValue().getAndSet(key, value);
        //默认设置一个小时过期
        redisTemplate.opsForValue().getOperations().expire(key, expireMsecs * 6, TimeUnit.MILLISECONDS);
        return (String) obj;
    }

    /**
     * 获取锁
     *
     * @return 获取锁成功返回ture，超时返回false
     * @throws InterruptedException 异常中断异常
     */
    public boolean lock() throws InterruptedException {
        int timeout = timeoutMsecs;
        while (timeout >= 0) {
            long expires = System.currentTimeMillis() + expireMsecs + 1;
            // 锁到期时间
            String expiresStr = String.valueOf(expires);
            if (this.setNX(lockKey, expiresStr)) {
                locked = true;
                return true;
            }
            // redis里key的时间
            String currentValue = this.get(lockKey);
            // 判断锁是否已经过期，过期则重新设置并获取
            if (currentValue != null && Long.parseLong(currentValue) < System.currentTimeMillis()) {
                // 设置锁并返回旧值
                String oldValue = this.getSet(lockKey, expiresStr);
                // 比较锁的时间，如果不一致则可能是其他锁已经修改了值并获取
                if (oldValue != null && oldValue.equals(currentValue)) {
                    locked = true;
                    return true;
                }
            }
            timeout -= DEFAULT_ACQUIRY_RETRY_MILLIS;
            // 延时
            Thread.sleep(DEFAULT_ACQUIRY_RETRY_MILLIS);
        }
        return false;
    }

    /**
     * 释放获取到的锁
     */
    public void unlock() {
        if (locked) {
            redisTemplate.delete(lockKey);
            locked = false;
        }
    }
}
