package com.kokogen.flu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokogen.flu.api.HeartBeatController;
import com.kokogen.flu.model.HeartBeat;
import com.kokogen.flu.model.Key;
import com.kokogen.flu.services.IHeartBeatService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebFluxTest(HeartBeatController.class)
public class TestHeartBeatHandler {
    @Autowired
    public WebTestClient client;

    @MockBean
    public IHeartBeatService heartBeatService;

    public static List<Key> persons = Stream.of("sveta", "kostya", "andre").map(Key::new).collect(Collectors.toList());
    public static List<Key> channels = Stream.of("mtv","a-one","euronews", "2x2").map(Key::new).collect(Collectors.toList());
    public static List<HeartBeat> heartBeatsForPerson = List.of(new HeartBeat("kostya", "euronews", "2022-04-19 10:00:00"), new HeartBeat("kostya", "2x2", "2022=04=19 12:23:23"));
    public static List<HeartBeat> heartBeatsForChannel = List.of(new HeartBeat("kostya", "2x2", "2022-04-19 10:00:00"), new HeartBeat("andre", "2x2", "2022=04=19 12:23:23"));

    @Test
    public void test_getAllPersons(){
        Mockito.when(heartBeatService.getAllPersons()).thenReturn(Flux.fromIterable(persons));

        client.get()
                .uri("http://localhost:8081/v1/hb/persons")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$[0].id").isEqualTo("sveta")
                .jsonPath("$[1].id").isEqualTo("kostya")
                .jsonPath("$[2].id").isEqualTo("andre");
    }

    @Test
    public void test_getAllChannels(){
        Mockito.when(heartBeatService.getAllChannels()).thenReturn(Flux.fromIterable(channels));

        client.get()
                .uri("http://localhost:8081/v1/hb/channels")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$[0].id").isEqualTo("mtv")
                .jsonPath("$[1].id").isEqualTo("a-one")
                .jsonPath("$[2].id").isEqualTo("euronews")
                .jsonPath("$[3].id").isEqualTo("2x2");
    }

    public static  HeartBeat deserialize(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, HeartBeat.class);
    }

    @Test
    public void test_getAllHeartBeatsByPerson(){
        Mockito.when(heartBeatService.getAllHeartBeatsByPerson("kostya")).thenReturn(Flux.fromIterable(heartBeatsForPerson));

        Flux<HeartBeat> actual =
                client
                        .get()
                        .uri("http://localhost:8081/v1/hb/persons/kostya")
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .returnResult(HeartBeat.class)
                        .getResponseBody();

        Assertions.assertArrayEquals(heartBeatsForPerson.toArray(), actual.toStream().toArray(HeartBeat[]::new));
    }

    @Test
    public void test_getAllHeartBeatsByChannel(){
        Mockito.when(heartBeatService.getAllHeartBeatsByChannel("2x2")).thenReturn(Flux.fromIterable(heartBeatsForChannel));

        Flux<HeartBeat> actual =
                client
                        .get()
                        .uri("http://localhost:8081/v1/hb/channels/2x2")
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .returnResult(HeartBeat.class)
                        .getResponseBody();

        Assertions.assertArrayEquals(heartBeatsForChannel.toArray(), actual.toStream().toArray(HeartBeat[]::new));
    }

    @Test
    public void test_save(){
        HeartBeat hb = new HeartBeat("kostya", "2x2", "2022-04-19 10:00:00");
        Mockito.when(heartBeatService.save(hb)).thenReturn(Mono.just(hb));
        client
            .post()
            .uri("http://localhost:8081/v1/hb")
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(hb), HeartBeat.class)
            .exchange()
            .expectStatus().isOk()
            .expectBody(HeartBeat.class).isEqualTo(hb);
    }

    @Test
    public void test_getHeartBeatByChannelAndPerson(){
        HeartBeat hb = new HeartBeat("kostya", "2x2", "2022-04-19 10:00:00");
        Mockito.when(heartBeatService.getHeartBeatByChannelAndPerson(hb.channel, hb.person)).thenReturn(Mono.just(hb));
        client
                .get()
                .uri("http://localhost:8081/v1/hb/find/2x2/kostya")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(HeartBeat.class).isEqualTo(hb);
    }


}
