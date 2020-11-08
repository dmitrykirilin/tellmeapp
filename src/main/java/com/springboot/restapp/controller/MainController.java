package com.springboot.restapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.springboot.restapp.model.User;
import com.springboot.restapp.model.Views;
import com.springboot.restapp.repo.MessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {

    private final MessageRepo messageRepo;
    private final ObjectWriter writer;

    @Autowired
    public MainController(MessageRepo messageRepo, ObjectMapper mapper) {
        this.messageRepo = messageRepo;
        writer = mapper
                .setConfig(mapper.getSerializationConfig())
                .writerWithView(Views.FullName.class);
    }

    @Value("${spring.profiles.active}")
    private String profile;

    @GetMapping
    public String main(@AuthenticationPrincipal User user,
                        Model model) throws JsonProcessingException {
        HashMap<Object, Object> frontendData = new HashMap<>();

        if(user != null) {
            frontendData.put("profile", user);

            String messages = writer.writeValueAsString(messageRepo.findAll());
            model.addAttribute("messages", messages);
        }
        model.addAttribute("frontendData", frontendData);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}
