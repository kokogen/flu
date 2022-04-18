package com.kokogen.flu.model;

import java.io.Serializable;

public class HeartBeat implements Serializable {
    public String person;
    public String channel;
    public String dt;

    public HeartBeat(String person, String channel, String dt){
        this.person = person;
        this.channel = channel;
        this.dt = dt;
    }

    @Override
    public String toString() {
        return "{" +
                "person: '" + person + '\'' +
                ", channel: '" + channel + '\'' +
                ", dt: '" + dt + '\'' +
                '}';
    }
}
