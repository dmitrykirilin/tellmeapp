package com.springboot.restapp.controller;

import com.springboot.restapp.model.User;
import com.springboot.restapp.repo.MessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {

    private final MessageRepo messageRepo;

    @Value("${spring.profiles.active}")
    private String profile;

    @GetMapping
    public String main(@AuthenticationPrincipal User user,
                        Model model){
        HashMap<Object, Object> frontendData = new HashMap<>();

        if(user != null) {
            frontendData.put("profile", user);
            frontendData.put("messages", messageRepo.findAll());
        }
        model.addAttribute("frontendData", frontendData);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}
