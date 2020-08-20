package com.springboot.restapp.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.springboot.restapp.dto.EventType;
import com.springboot.restapp.dto.ObjectType;
import com.springboot.restapp.model.Message;
import com.springboot.restapp.model.Views;
import com.springboot.restapp.repo.MessageRepo;
import com.springboot.restapp.util.WsSender;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("rest/v1")
public class MessageRestController {

    private final MessageRepo messageRepo;
    private final BiConsumer<EventType, Message> wsSender;

    public MessageRestController(MessageRepo messageRepo, WsSender wsSender) {
        this.messageRepo = messageRepo;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.IdName.class);
    }

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
        wsSender.accept(EventType.REMOVE, message);

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

        wsSender.accept(EventType.CREATE, storageMessage);

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
        Message updatedMessage = messageRepo.save(message);

        wsSender.accept(EventType.UPDATE, updatedMessage);

        return ResponseEntity.ok(message);
    }

//    @MessageMapping("/changeMessage")
//    @SendTo("/topic/activity")
//    public Message change(Message message){
//        return messageRepo.save(message);
//    }
}
