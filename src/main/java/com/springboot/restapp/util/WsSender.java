package com.springboot.restapp.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.springboot.restapp.dto.EventType;
import com.springboot.restapp.dto.ObjectType;
import com.springboot.restapp.dto.WsEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component
@RequiredArgsConstructor
public class WsSender {

    private final SimpMessagingTemplate template;
    private final ObjectMapper objectMapper;

    public <T> BiConsumer<EventType, T> getSender(ObjectType objectType,
                                                  Class view){
        ObjectWriter writer = objectMapper.setConfig(objectMapper.getSerializationConfig())
                .writerWithView(view);
        return (EventType eventType, T payload) -> {
            String valueAsString = null;

            try{
                valueAsString = writer.writeValueAsString(payload);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            template.convertAndSend(
                    "/topic/activity",
                    new WsEventDto(objectType, eventType, valueAsString)
            );
        };
    }
}
