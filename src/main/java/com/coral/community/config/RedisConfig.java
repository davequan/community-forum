package com.coral.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // setting key serialization ways
        template.setKeySerializer(RedisSerializer.string());
        //setting value serialization ways
        template.setValueSerializer(RedisSerializer.json());
        //setting hash's key way serialization ways
        template.setHashKeySerializer(RedisSerializer.string());
        //setting hash's value serialization ways
        template.setHashValueSerializer(RedisSerializer.json());
        template.afterPropertiesSet();
        return  template;
    }
}
