package com.coral.community.event;

import com.alibaba.fastjson.JSONObject;
import com.coral.community.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventProducer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    //handle event
    public void fireEvent(Event event){
        // send event to specific topic
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));

    }
}
