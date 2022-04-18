package com.kokogen.flu;

import com.kokogen.flu.model.Key;
import com.kokogen.flu.services.IHeartBeatService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
public class TestHeartBeatHandler {

    WebTestClient client;

    @Mock
    private IHeartBeatService heartBeatService;

    private List<Key> persons;
    private List<Key> channels;

    @Before
    public void init(){
        persons = Stream.of("Sveta", "Kostya", "Andre").map(Key::new).collect(Collectors.toList());
        channels = Stream.of("MTV","A-One","Euronews", "2x2").map(Key::new).collect(Collectors.toList());
        client = WebTestClient.bindToServer().baseUrl("http://localhost/v1/hb").build();
        heartBeatService = new TestHeartBeatService();
    }

    @Test
    public void test_getAllPersons() throws Exception{
        client.get()
                .uri("/persons")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Key.class)
                .isEqualTo(persons);
    }

    @Test
    public void test_getAllChannels() throws Exception{
        client.get()
                .uri("/channels")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Key.class)
                .isEqualTo(channels);
    }
}
