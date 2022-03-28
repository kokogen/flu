package com.kokogen.flu;

import com.kokogen.flu.model.HeartBeat;
import com.kokogen.flu.services.IHeartBeatPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kokogen.flu.FluApplication.logger;

@RestController
@RequestMapping("{$baseUrl}")

public class HeartBeatController {
    @Autowired
    private IHeartBeatPublisher publisher;

    @PostMapping(path = "/hb")
    public void publish(@RequestBody HeartBeat heartBeat) {
        logger.info("HeartBeatController.publish({}) - begin.", heartBeat);
        publisher.publish(heartBeat);
        logger.info("HeartBeatController.publish({}) - end.", heartBeat);
    }

    @GetMapping(path = "/hb/persons/")
    public List<String> getAllPersons() {
        return publisher.getAllPersons();
    }

    @GetMapping(path = "/hb/channels/")
    public List<String> getAllChannels() {
        return publisher.getAllChannels();
    }

    @GetMapping(path = "/hb/persons/{person}")
    public List<String> getAllHeartBeatsByPerson(@PathVariable String person) {
        return publisher.getAllHeartBeatsByPerson(person);
    }

    @GetMapping(path = "/hb/channels/{channel}")
    public List<String> getAllHeartBeatsByChannel(@PathVariable String channel) {
        return publisher.getAllHeartBeatsByChannel(channel);
    }
}
