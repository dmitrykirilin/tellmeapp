package com.springboot.restapp.services;


import com.springboot.restapp.model.Comment;
import com.springboot.restapp.model.User;
import com.springboot.restapp.repo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepo commentRepo;

    @Autowired
    public CommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public Comment create(Comment comment, User author){
        comment.setAuthor(author);
        return commentRepo.save(comment);
    }
}
