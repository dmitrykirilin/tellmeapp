package com.springboot.restapp.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.springboot.restapp.model.Views;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.swing.event.DocumentEvent;

@Data
@AllArgsConstructor
@JsonView(Views.IdName.class)
public class WsEventDto {
    private ObjectType objectType;
    private EventType eventType;
    @JsonRawValue
    private String body;
}
