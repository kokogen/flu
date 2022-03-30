package com.kokogen.flu.services;

import com.kokogen.flu.model.HeartBeat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Component
public class HeartBeatPublisherImpl implements IHeartBeatPublisher{
    public static int EXPIRATION_DUR = 10;
    public static String PREFIX_FOR_PERSON = "person:";
    public static String PREFIX_FOR_CHANNEL = "channel:";
    public static String PREFIX_FOR_CHANNEL_AND_PERSON = "chanpsn:";
    public static String PREFIX_FOR_TOPIC = "topic:";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void publish(HeartBeat heartBeat) {
        ArrayList<HeartBeat> lst = new ArrayList<>();

        heartBeat.dt = (new Date()).toString();
        this.redisTemplate.opsForValue().set(heartBeat.person, heartBeat.toString());//.timeout(Duration.ofMinutes(EXPIRATION_DUR));
        this.redisTemplate.opsForSet().add(PREFIX_FOR_PERSON + heartBeat.person, heartBeat.toString());//.timeout(Duration.ofMinutes(EXPIRATION_DUR));
        this.redisTemplate.opsForSet().add(PREFIX_FOR_CHANNEL + heartBeat.channel, heartBeat.toString());//.timeout(Duration.ofMinutes(EXPIRATION_DUR));
        this.redisTemplate.opsForHash().put(PREFIX_FOR_CHANNEL_AND_PERSON + heartBeat.channel, heartBeat.person, heartBeat.toString());//.timeout(Duration.ofMinutes(EXPIRATION_DUR));
        this.redisTemplate.convertAndSend(PREFIX_FOR_TOPIC + heartBeat.channel, heartBeat.toString());//.timeout(Duration.ofMinutes(EXPIRATION_DUR));
    }

    @Override
    public List<String> getAllPersons() {
        return new ArrayList<>(Objects.requireNonNull(this.redisTemplate.keys(PREFIX_FOR_PERSON + "*")));
    }

    @Override
    public List<String> getAllChannels() {
        return new ArrayList<>(Objects.requireNonNull(this.redisTemplate.keys(PREFIX_FOR_CHANNEL + "*")));
    }

    @Override
    public List<String> getAllHeartBeatsByPerson(String person) {
        ArrayList<String> r = new ArrayList<>();
        Set<String> keys = this.redisTemplate.keys(PREFIX_FOR_PERSON + person);
        if (keys == null) return r;
        for(String key: keys) {
            Cursor<String> crs = this.redisTemplate.opsForSet().scan(key, ScanOptions.scanOptions().build());
            while(crs.hasNext()) r.add(crs.next());
        }
        return r;
    }

    @Override
    public List<String> getAllHeartBeatsByChannel(String channel) {
        ArrayList<String> r = new ArrayList<>();
        Set<String> keys = this.redisTemplate.keys(PREFIX_FOR_CHANNEL + channel);
        if (keys == null) return r;
        for(String key: keys) {
            Cursor<String> crs = this.redisTemplate.opsForSet().scan(key, ScanOptions.scanOptions().build());
            while(crs.hasNext()) r.add(crs.next());
        }
        return r;
    }
}
