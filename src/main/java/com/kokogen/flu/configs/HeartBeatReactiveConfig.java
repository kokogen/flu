package com.kokogen.flu.configs;

import com.kokogen.flu.api.HeartBeatHandler;
import com.kokogen.flu.model.HeartBeat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.*;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class HeartBeatReactiveConfig {

    @Value("${redisHost}")
    private String redisHost;

    @Value("${redisPort}")
    private int redisPort;
/*
    @Bean
    public RouterFunction<ServerResponse> route(HeartBeatHandler handler) {
        return RouterFunctions
                .route(GET("/v1/hb/persons").and(accept(MediaType.APPLICATION_JSON)), handler::getAllPersons)
                .andRoute(GET("/v1/hb/channels").and(accept(MediaType.APPLICATION_JSON)), handler::getAllChannels)
                .andRoute(GET("/v1/hb/persons/{person}").and(accept(MediaType.APPLICATION_JSON)), handler::getAllHeartBeatsByPerson)
                .andRoute(GET("/v1/hb/channels/{channel}").and(accept(MediaType.APPLICATION_JSON)), handler::getAllHeartBeatsByChannel)
                .andRoute(GET("/v1/hb/find/{channel}/{person}").and(accept(MediaType.APPLICATION_JSON)), handler::getHeartBeatByChannelAndPerson)
                .andRoute(POST("/v1/hb").and(accept(MediaType.APPLICATION_JSON)), handler::save);
    }
*/
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