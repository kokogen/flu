package com.kokogen.flu.api;

import com.kokogen.flu.model.HeartBeat;
import com.kokogen.flu.model.Key;
import com.kokogen.flu.services.IHeartBeatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/hb")
public class HeartBeatController {
    public static Logger logger = LoggerFactory.getLogger(HeartBeatController.class);
    @Autowired
    private IHeartBeatService heartBeatService;

    @GetMapping("/persons")
    public Flux<Key> getAllPersons(){
        return heartBeatService.getAllPersons().doOnError(x -> logger.error(String.valueOf(x)));
    }

    @GetMapping("/channels")
    public Flux<Key> getAllChannels(){
        return heartBeatService.getAllChannels().doOnError(x -> logger.error(String.valueOf(x)));
    }

    @GetMapping("/persons/{person}")
    public Flux<HeartBeat> getAllHeartBeatsByPerson(@PathVariable String person){
        return heartBeatService.getAllHeartBeatsByPerson(person).doOnError(x -> logger.error(String.valueOf(x)));
    }

    @GetMapping("/channels/{channel}")
    public Flux<HeartBeat> getAllHeartBeatsByChannel(@PathVariable String channel){
        return heartBeatService.getAllHeartBeatsByChannel(channel).doOnError(x -> logger.error(String.valueOf(x)));
    }

    @GetMapping("/find/{channel}/{person}")
    public Mono<HeartBeat> getHeartBeatByChannelAndPerson(@PathVariable String channel, @PathVariable String person){
        return heartBeatService.getHeartBeatByChannelAndPerson(channel, person).doOnError(x -> logger.error(String.valueOf(x)));
    }

    @PostMapping
    public Mono<HeartBeat> save(@RequestBody HeartBeat heartBeat){
        return heartBeatService.save(heartBeat).doOnError(x -> logger.error(String.valueOf(x)));
    }
}
