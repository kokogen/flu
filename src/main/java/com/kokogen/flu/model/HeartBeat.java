package com.kokogen.flu.model;

import java.io.Serializable;

public class HeartBeat implements Serializable {
    public String person;
    public String channel;
    public String dt;


    @Override
    public String toString() {
        return "{" +
                "person: '" + person + '\'' +
                ", channel: '" + channel + '\'' +
                ", dt: '" + dt + '\'' +
                '}';
    }
}
