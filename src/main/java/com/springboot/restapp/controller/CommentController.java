package com.springboot.restapp.controller;

import com.springboot.restapp.model.Comment;
import com.springboot.restapp.model.User;
import com.springboot.restapp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public Comment create(
            @RequestBody Comment comment,
            @AuthenticationPrincipal User user
            ){
        return commentService.create(comment, user);
    }
}
