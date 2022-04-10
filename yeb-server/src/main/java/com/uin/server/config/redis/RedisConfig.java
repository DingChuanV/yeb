package com.uin.server.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author wanglufei
 * \* Date: 2021年08月08日 19:10
 * \* Description: redis配置类
 * \
 */
@Configuration
public class RedisConfig {

    /**
     * @param connectionFactory
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/9 8:23
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //String类型 key序列器
        template.setKeySerializer(new StringRedisSerializer());
        //String类型 value序列器
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //Hash类型 key序列器
        template.setHashKeySerializer(new StringRedisSerializer());
        //Hash类型 value序列器
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
