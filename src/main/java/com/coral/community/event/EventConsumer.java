package com.coral.community.event;

import com.alibaba.fastjson.JSONObject;
import com.coral.community.entity.DiscussPost;
import com.coral.community.entity.Event;
import com.coral.community.entity.Message;
import com.coral.community.service.DiscussPostService;
import com.coral.community.service.ElasticSearchService;
import com.coral.community.service.MessageService;
import com.coral.community.util.CommunityConstant;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class EventConsumer implements CommunityConstant {
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    @Autowired
    private MessageService messageService;
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private ElasticSearchService elasticSearchService;




    // someone like/follow/message you
    @KafkaListener(topics={TOPIC_COMMENT,TOPIC_LIKE,TOPIC_FOLLOW})
    public  void handleCommentMessage(ConsumerRecord record){
        if(record == null || record.value() == null){
            logger.error("message is null!");
            return;
        }
        Event event = JSONObject.parseObject(record.value().toString(),Event.class);
        if(event == null){
            logger.error("format is incorrect");
            return;
        }
        //send notification
        Message message = new Message();
        message.setFromId(SYSTEM_USER_ID);
        message.setToId(event.getEntityUserId());
        message.setConversationId(event.getTopic());
        message.setCreateTime(new Date());
        Map<String,Object>content = new HashMap<>();
        content.put("userId",event.getUserId());
        content.put("entityType",event.getEntityType());
        content.put("entityId",event.getEntityId());
        if(!event.getData().isEmpty()){
            for(Map.Entry<String,Object>entry:event.getData().entrySet()) {
                content.put(entry.getKey(), entry.getValue());
            }
        }
        message.setContent(JSONObject.toJSONString(content));
        messageService.addMessage(message);
    }
    // consumer post event
    @KafkaListener(topics = {TOPIC_PUBLISH})
    public  void handlePublishMessage(ConsumerRecord record) {
        if(record == null || record.value() == null){
            logger.error("message is null!");
            return;
        }
        Event event = JSONObject.parseObject(record.value().toString(),Event.class);
        if(event == null){
            logger.error("format is incorrect");
            return;
        }
        DiscussPost discussPost = discussPostService.findDiscussPostById(event.getEntityId());
        elasticSearchService.saveDiscussPost(discussPost);



    }




}
