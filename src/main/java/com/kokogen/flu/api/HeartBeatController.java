package com.kokogen.flu.api;

import com.kokogen.flu.model.HeartBeat;
import com.kokogen.flu.model.Key;
import com.kokogen.flu.services.IHeartBeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/hb")
public class HeartBeatController {
    @Autowired
    private IHeartBeatService heartBeatService;

    @GetMapping("/persons")
    public Flux<Key> getAllPersons(){
        return heartBeatService.getAllPersons().onErrorReturn(FluException.class, new Key());
    }

    @GetMapping("/channels")
    public Flux<Key> getAllChannels(){
        return heartBeatService.getAllChannels().onErrorReturn(FluException.class, new Key());
    }

    @GetMapping("/persons/{person}")
    public Flux<HeartBeat> getAllHeartBeatsByPerson(@PathVariable String person){
        return heartBeatService.getAllHeartBeatsByPerson(person).onErrorReturn(FluException.class, new HeartBeat());
    }

    @GetMapping("/channels/{channel}")
    public Flux<HeartBeat> getAllHeartBeatsByChannel(@PathVariable String channel){
        return heartBeatService.getAllHeartBeatsByChannel(channel).onErrorReturn(FluException.class, new HeartBeat());
    }

    @GetMapping("/find/{channel}/{person}")
    public Mono<HeartBeat> getHeartBeatByChannelAndPerson(@PathVariable String channel, @PathVariable String person){
        return heartBeatService.getHeartBeatByChannelAndPerson(channel, person).onErrorReturn(FluException.class, new HeartBeat());
    }

    @PostMapping
    public Mono<HeartBeat> save(@RequestBody HeartBeat heartBeat){
        return heartBeatService.save(heartBeat).onErrorReturn(FluException.class, new HeartBeat());
    }
}
