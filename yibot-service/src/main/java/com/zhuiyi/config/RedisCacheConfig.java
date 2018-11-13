package com.zhuiyi.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuiyi.common.constant.GlobaSystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tree
 * @version 1.0
 * date: 2018/7/26 15:34
 * description: xxx
 * own:
 */
@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {

    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;

    /**
     * 自定义缓存管理器
     */
    @Bean
    public RedisCacheManager cacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
        Map<String, RedisCacheConfiguration> initialCacheConfigurations = new HashMap<>(10);
        //设置默认配置,默认缓存一天
        RedisCacheConfiguration defaultCacheConfiguration = getRedisCacheConfiguration(Duration.ofHours(24));
        //当redis注解value为“10min”时候，采用下面这个配置
        initialCacheConfigurations.put("10min", getRedisCacheConfiguration(Duration.ofMinutes(10L)));
        initialCacheConfigurations.put("1h", getRedisCacheConfiguration(Duration.ofHours(1L)));
        initialCacheConfigurations.put("6h", getRedisCacheConfiguration(Duration.ofHours(6L)));
        initialCacheConfigurations.put("3d", getRedisCacheConfiguration(Duration.ofDays(3)));
        return RedisCacheManager.builder(lettuceConnectionFactory)
                .cacheDefaults(defaultCacheConfiguration)
                .withInitialCacheConfigurations(initialCacheConfigurations)
                .transactionAware()
                .build();
    }

    /**
     * 自定义的缓存key的生成策略
     */
    @Bean
    public KeyGenerator commonKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getSimpleName());
            sb.append(":");
            sb.append(method.getName());
            for(Object obj:params){
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    /**
     * 自定义缓存配置
     */
    private RedisCacheConfiguration getRedisCacheConfiguration(Duration duration) {
        //获取默认配置
        RedisCacheConfiguration defaultCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        return defaultCacheConfiguration
                .disableCachingNullValues()
                .computePrefixWith(cacheName -> GlobaSystemConstant.KEY_PROJECT_NAME_STR.concat(":").concat(cacheName).concat(":"))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(genericJackson2JsonRedisSerializer))
                .entryTtl(duration);
    }

    /**
     * 设置RedisTemplate序列化
     */
    @SuppressWarnings("unchecked")
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory ) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        RedisSerializer stringSerializer = new StringRedisSerializer();
        //key序列化
        redisTemplate.setKeySerializer(stringSerializer);
        //value序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //Hash key序列化
        redisTemplate.setHashKeySerializer(stringSerializer);
        //Hash value序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
