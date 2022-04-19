package com.kokogen.flu.api;

import com.kokogen.flu.model.HeartBeat;
import com.kokogen.flu.model.Key;
import com.kokogen.flu.services.IHeartBeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

public class HeartBeatHandler {
    @Autowired
    private IHeartBeatService heartBeatService;

    public Mono<ServerResponse> save(ServerRequest request){
        final Mono<HeartBeat> hbs = request.bodyToMono(HeartBeat.class);
        return hbs.flatMap(hb ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(heartBeatService.save(hb), HeartBeat.class));
    }

    public Mono<ServerResponse> getAllPersons(ServerRequest request){
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(heartBeatService.getAllPersons(), Key.class);
    }

    public Mono<ServerResponse> getAllChannels(ServerRequest request){
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(heartBeatService.getAllChannels(), Key.class);
    }

    public Mono<ServerResponse> getAllHeartBeatsByPerson(ServerRequest request){
        String person = request.pathVariable("person");
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(heartBeatService.getAllHeartBeatsByPerson(person), HeartBeat.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getAllHeartBeatsByChannel(ServerRequest request){
        String channel = request.pathVariable("channel");
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(heartBeatService.getAllHeartBeatsByChannel(channel), HeartBeat.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getHeartBeatByChannelAndPerson(ServerRequest request){
        String channel = request.pathVariable("channel");
        String person = request.pathVariable("person");
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(heartBeatService.getHeartBeatByChannelAndPerson(channel, person), HeartBeat.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}

