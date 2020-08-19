package com.springboot.restapp.controller;
import com.fasterxml.jackson.annotation.JsonView;
import com.springboot.restapp.model.Message;
import com.springboot.restapp.model.Views;
import com.springboot.restapp.repo.MessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("rest/v1")
@RequiredArgsConstructor
public class MessageRestController {

    private final MessageRepo messageRepo;

    @JsonView(Views.IdName.class)
    @GetMapping(value = "messages", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Message>> getAll(){
        List<Message> messages = messageRepo.findAll();
        return ResponseEntity.ok(messages);
    }

    @JsonView(Views.FullName.class)
    @GetMapping(value = "messages/{message}")
    public ResponseEntity<?> getById(@PathVariable Message message){
        if(message == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(message);
    }


    @JsonView(Views.IdName.class)
    @DeleteMapping(value = "messages/{message}")
    public ResponseEntity<?> removeById(@PathVariable Message message){
        if(message == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        messageRepo.delete(message);
        return ResponseEntity.ok("Message with id " + message.getId() + " was successfully removed");
    }


    @JsonView(Views.IdName.class)
    @PostMapping(value = "messages")
    public ResponseEntity<?> addNewMessage(@RequestBody Message message){
        if(message == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        message.setCreationDate(LocalDateTime.now());
        Message storageMessage = messageRepo.save(message);

        return ResponseEntity.ok(storageMessage);
    }


    @JsonView(Views.IdName.class)
    @PutMapping(value = "messages/{message}")
    public ResponseEntity<?> addNewMessage(@PathVariable Message message,
                                            @RequestBody Message newMessage){
        if(message == null || newMessage == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BeanUtils.copyProperties(newMessage, message, "id");
        messageRepo.save(message);

        return ResponseEntity.ok(message);
    }

    @MessageMapping("/changeMessage")
    @SendTo("/topic/activity")
    public Message change(Message message){
        return messageRepo.save(message);
    }
}
