package com.kokogen.flu.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import static com.kokogen.flu.FluApplication.logger;

@Configuration
public class HeartBeatServiceConfig {
    @Value("${redisHost:redis}")
    private String redisHost;

    @Value("${redisPort}")
    private int redisPort;

    @Bean
    LettuceConnectionFactory connectionFactory(){
        RedisStandaloneConfiguration a = new RedisStandaloneConfiguration(redisHost, redisPort);
        return new LettuceConnectionFactory(a);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        final RedisTemplate<String, String> template = new RedisTemplate<String, String>();
        LettuceConnectionFactory connectionFactory = connectionFactory();

        template.setConnectionFactory(connectionFactory);
        template.setDefaultSerializer(new StringRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericToStringSerializer<String>(String.class));

        logger.info("3: redisHost={}, redisPort={}", redisHost, redisPort);
        return template;
    }

}
