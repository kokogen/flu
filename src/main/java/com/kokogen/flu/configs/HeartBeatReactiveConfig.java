package com.kokogen.flu.configs;

import com.kokogen.flu.model.HeartBeat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.*;

@Configuration
public class HeartBeatReactiveConfig {

    @Value("${redisHost}")
    private String redisHost;

    @Value("${redisPort}")
    private int redisPort;

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfig = new RedisStandaloneConfiguration();
        redisStandaloneConfig.setHostName(redisHost);
        redisStandaloneConfig.setPort(redisPort);
        return new LettuceConnectionFactory(redisStandaloneConfig);
    }

    @Bean
    public ReactiveRedisOperations<String, HeartBeat> redisOperations(LettuceConnectionFactory connectionFactory) {

        Jackson2JsonRedisSerializer<HeartBeat> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(HeartBeat.class);

        RedisSerializationContext<String, HeartBeat> serializationContext = RedisSerializationContext
                .<String, HeartBeat>newSerializationContext(new StringRedisSerializer())
                .key(new StringRedisSerializer())
                .value(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .hashKey(new StringRedisSerializer())
                .hashValue(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .build();
        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }
}
