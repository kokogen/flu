package com.kokogen.flu.services;

import com.kokogen.flu.model.HeartBeat;
import com.kokogen.flu.model.Key;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IHeartBeatService {
    Mono<HeartBeat> save(HeartBeat heartBeat);
    Flux<Key> getAllPersons();
    Flux<Key> getAllChannels();
    Flux<HeartBeat> getAllHeartBeatsByPerson(String person);
    Flux<HeartBeat> getAllHeartBeatsByChannel(String channel);
    Mono<HeartBeat> getHeartBeatByChannelAndPerson(String channel, String person);
}
