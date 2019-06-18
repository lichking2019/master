package com.wt.master.core.config;

import com.wt.master.core.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;


/**
 * 缓存配置
 *
 * @author lichking2019@aliyun.com
 * @date Apr 15, 2019 at 10:48:09 PM
 */
@Configuration
@EnableCaching
@Slf4j
public class CacheConfiguration extends CachingConfigurerSupport {
    /**
     * Spring提供的redis操作模板，定义序列化方式
     *
     * @param factory
     * @return
     */
    @Bean
    @ConditionalOnExpression("${com.wt.framework.config.cache.enable}==true&&'${com.wt.framework.config.cache.type}'" +
            ".equals('redis')")
    public RedisTemplate<Object, Object> redisTemplate(JedisConnectionFactory factory) {
        log.info("发现客户端的redis配置，加载redis实现：[{}]", RedisTemplate.class.getName());
        RedisTemplate<Object, Object> templateFor = new RedisTemplate<Object, Object>();
        templateFor.setConnectionFactory(factory);
        //        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer =
                new GenericJackson2JsonRedisSerializer();

        //设置String结构的key序列化方式
        templateFor.setKeySerializer(new StringRedisSerializer());
        //设置String结构的value序列化方式
        templateFor.setValueSerializer(genericJackson2JsonRedisSerializer);
        //设置hash结构的value序列化方式
        templateFor.setHashKeySerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        //设置hash结构的key序列化方式
        templateFor.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        return templateFor;
    }

    /**
     * redis的CacheManager
     *
     * @param redisTemplate
     * @param factory
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(CacheService.class)
    @ConditionalOnExpression("${com.wt.framework.config.cache.enable}==true&&'${com.wt.framework.config.cache.type}'" +
            ".equals('redis')")
    public CacheManager redisCacheManager(RedisTemplate redisTemplate, JedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter =
                RedisCacheWriter.nonLockingRedisCacheWriter(redisTemplate.getConnectionFactory());
        RedisCacheConfiguration redisCacheConfiguration =
                RedisCacheConfiguration.defaultCacheConfig().serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));//设置序列化
        //设置默认超过期时间是30秒
        redisCacheConfiguration.entryTtl(Duration.ofSeconds(30));
        //初始化RedisCacheManager
        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
        return cacheManager;
    }

    /**
     * ehcache的CachManager
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(CacheService.class)
    @ConditionalOnExpression("${com.wt.framework.config.cache.enable}==true&&'${com.wt.framework.config.cache.type}'" +
            ".equals('ehCache')")
    public CacheManager ehCacheManager() {
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
        //指定ehcache配置文件
        ehCacheCacheManager.setCacheManager(net.sf.ehcache.CacheManager.create("ehcache.xml"));
        return ehCacheCacheManager;
    }

    /**
     * 缓存异常处理
     *
     * @return
     */
    @Override
    @Bean
    public CacheErrorHandler errorHandler() {
        log.info("初始化->[{}]", "Redis CacheErrorHandler");
        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                log.error("Redis 在执行Get的时候，出现异常。key[{}]", key, exception);
            }

            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                log.error("Redis 在执行Put的时候，出现异常。key[{}]", key, exception);
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                log.error("Redis 在执行Evict的时候，出现异常。key[{}]", key, exception);
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                log.error("Redis 在执行Clear的时候，出现异常", exception);
            }
        };
        return cacheErrorHandler;
    }


}
