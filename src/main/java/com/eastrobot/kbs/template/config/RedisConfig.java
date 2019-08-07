package com.eastrobot.kbs.template.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2019-03-15 21:45
 */
@Configuration
@EnableCaching
@ConfigurationProperties(prefix = "spring.cache")
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    @Primary
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // set default serializer
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(fastJsonSerializer());
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(fastJsonSerializer());

        return template;
    }

    @Bean
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(determineConfiguration())
                .build();
    }

    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(method.getName());
            if (params.length > 0) {
                sb.append(":");
                sb.append(params[0]);
                for (int i = 1; i < params.length; i++) {
                    sb.append(",").append(params[i]);
                }
            }
            return sb.toString();
        };
    }

    /**
     * copy from RedisCacheConfiguration and made some changes
     */
    private RedisCacheConfiguration determineConfiguration() {
        CacheProperties.Redis redisProperties = this.redis;
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            config = config.prefixKeysWith(redisProperties.getKeyPrefix());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }

        // update by Yogurt_lei on 2019-05-05 14:41 : change value serializer to json
        config =
                config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()));
        config =
                config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(fastJsonSerializer()));

        return config;
    }

    private static class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

        private Class<T> clazz;

        FastJsonRedisSerializer(Class<T> clazz) {
            super();
            this.clazz = clazz;
        }

        @Override
        public byte[] serialize(T t) throws SerializationException {
            if (t == null) {
                return new byte[0];
            }
            return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(StandardCharsets.UTF_8);

        }

        @Override
        public T deserialize(byte[] bytes) throws SerializationException {
            if (bytes == null || bytes.length <= 0) {
                return null;
            }
            String str = new String(bytes, StandardCharsets.UTF_8);
            return JSON.parseObject(str, clazz);
        }
    }

    private RedisSerializer fastJsonSerializer() {
        final FastJsonRedisSerializer<Object> fastJsonRedisSerializer =
                new FastJsonRedisSerializer<>(Object.class);
        // update by Yogurt_lei on 2019-05-08 10:30 : https://github.com/alibaba/fastjson/wiki/enable_autotype
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        return fastJsonRedisSerializer;
    }

    private final CacheProperties.Redis redis = new CacheProperties.Redis();

    public CacheProperties.Redis getRedis() {
        return this.redis;
    }

    /**
     * Redis-specific cache properties. copy from CacheProperties#Redis
     */
    @Getter
    @Setter
    public static class Redis {

        /**
         * Entry expiration. By default the entries never expire.
         */
        private Duration timeToLive;

        /**
         * Allow caching null values.
         */
        private boolean cacheNullValues = true;

        /**
         * Key prefix.
         */
        private String keyPrefix;

        /**
         * Whether to use the key prefix when writing to Redis.
         */
        private boolean useKeyPrefix = true;
    }

}