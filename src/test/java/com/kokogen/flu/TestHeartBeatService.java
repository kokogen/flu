package com.kokogen.flu;

import com.kokogen.flu.model.HeartBeat;
import com.kokogen.flu.model.Key;
import com.kokogen.flu.services.IHeartBeatService;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
@ActiveProfiles("test")
public class TestHeartBeatService implements IHeartBeatService {

    private ArrayList<HeartBeat> data = new ArrayList<>();

    public TestHeartBeatService(){
        data.add(new HeartBeat("Sveta","MTV","2022-04-12 09:12:23"));
        data.add(new HeartBeat("Sveta","A-One","2022-04-12 10:01:33"));
        data.add(new HeartBeat("Sveta","Euronews","2022-04-13 19:12:23"));
        data.add(new HeartBeat("Sveta","Euronews","2022-04-14 20:52:00"));
        data.add(new HeartBeat("Kostya","Euronews","2022-04-12 21:00:00"));
        data.add(new HeartBeat("Kostya","Euronews","2022-04-12 21:12:00"));
        data.add(new HeartBeat("Kostya","Euronews","2022-04-12 21:39:00"));
        data.add(new HeartBeat("Kostya","2x2","2022-04-12 23:00:00"));
        data.add(new HeartBeat("Kostya","2x2","2022-04-12 23:59:00"));
        data.add(new HeartBeat("Andre","Euronews","2022-04-13 08:00:00"));
        data.add(new HeartBeat("Andre","Euronews","2022-04-13 08:10:00"));
        data.add(new HeartBeat("Andre","A-One","2022-04-14 13:10:00"));
        data.add(new HeartBeat("Andre","A-One","2022-04-14 19:10:00"));
    }

    @Override
    public Mono<HeartBeat> save(HeartBeat heartBeat) {
        return Mono.just(heartBeat);
    }

    @Override
    public Flux<Key> getAllPersons() {
        return Flux.fromStream(data.stream()).map(hb -> hb.person).distinct().map(Key::new);
    }

    @Override
    public Flux<Key> getAllChannels() {
        return Flux.fromStream(data.stream()).map(hb -> hb.channel).distinct().map(Key::new);
    }

    @Override
    public Flux<HeartBeat> getAllHeartBeatsByPerson(String person) {
        return Flux.fromStream(data.stream()).filter(hb -> hb.person.equals(person));
    }

    @Override
    public Flux<HeartBeat> getAllHeartBeatsByChannel(String channel) {
        return Flux.fromStream(data.stream()).filter(hb -> hb.channel.equals(channel));
    }

    @Override
    public Mono<HeartBeat> getHeartBeatByChannelAndPerson(String channel, String person) {
        return Mono.just(
                data.stream()
                        .filter(hb -> hb.person.equals(person) && hb.channel.equals((channel)))
                        .findFirst()
                        .get()
        );
    }
}
