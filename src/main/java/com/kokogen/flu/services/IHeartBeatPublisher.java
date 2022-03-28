package com.kokogen.flu.services;

import com.kokogen.flu.model.HeartBeat;

import java.util.List;

public interface IHeartBeatPublisher {
    void publish(HeartBeat heartBeat);
    List<String> getAllPersons();
    List<String> getAllChannels();
    List<String> getAllHeartBeatsByPerson(String person);
    List<String> getAllHeartBeatsByChannel(String channel);
}
