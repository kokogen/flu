package com.kokogen.flu.services;

import com.kokogen.flu.model.HeartBeat;
import com.kokogen.flu.model.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HeartBeatService implements IHeartBeatService {
    public static int EXPIRATION_DUR = 10;
    public static String PREFIX_FOR_PERSON = "person:";
    public static String PREFIX_FOR_CHANNEL = "channel:";
    public static String PREFIX_FOR_CHANNEL_AND_PERSON = "chanpsn:";

    @Autowired
    private ReactiveRedisOperations<String, HeartBeat> redisOperations;

    @Override
    public Mono<HeartBeat> save(HeartBeat heartBeat) {

        return redisOperations.opsForSet().add(PREFIX_FOR_PERSON + heartBeat.person, heartBeat)
                .then(redisOperations.opsForSet().add(PREFIX_FOR_CHANNEL + heartBeat.channel, heartBeat))
                .then(redisOperations.opsForHash().put(PREFIX_FOR_CHANNEL_AND_PERSON + heartBeat.channel, heartBeat.person, heartBeat))
                .then(Mono.just(heartBeat));
    }

    @Override
    public Flux<Key> getAllPersons() {
        return redisOperations.keys(PREFIX_FOR_PERSON + "*").map(s -> s.substring(PREFIX_FOR_PERSON.length())).map(Key::new);
    }

    @Override
    public Flux<Key> getAllChannels() {
        return redisOperations.keys(PREFIX_FOR_CHANNEL + "*").map(s -> s.substring(PREFIX_FOR_CHANNEL.length())).map(Key::new);
    }

    @Override
    public Flux<HeartBeat> getAllHeartBeatsByPerson(String person) {
        return redisOperations.opsForSet().scan(PREFIX_FOR_PERSON + person);
    }

    @Override
    public Flux<HeartBeat> getAllHeartBeatsByChannel(String channel) {
        return redisOperations.opsForSet().scan(PREFIX_FOR_CHANNEL + channel);
    }

    @Override
    public Mono<HeartBeat> getHeartBeatByChannelAndPerson(String channel, String person){
        return redisOperations.opsForHash().get(PREFIX_FOR_CHANNEL + channel, PREFIX_FOR_PERSON + person).cast(HeartBeat.class);
    }
}
