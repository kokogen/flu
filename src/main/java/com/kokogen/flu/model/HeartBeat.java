package com.kokogen.flu.model;

import java.io.Serializable;
import java.util.Objects;

public class HeartBeat implements Serializable {
    public String person;
    public String channel;
    public String dt;

    public HeartBeat(String person, String channel, String dt){
        this.person = person;
        this.channel = channel;
        this.dt = dt;
    }

    public HeartBeat(){}

    @Override
    public String toString() {
        return "{" +
                "person: '" + person + '\'' +
                ", channel: '" + channel + '\'' +
                ", dt: '" + dt + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeartBeat heartBeat = (HeartBeat) o;
        return Objects.equals(person, heartBeat.person) && Objects.equals(channel, heartBeat.channel) && Objects.equals(dt, heartBeat.dt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, channel, dt);
    }
}
