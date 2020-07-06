package com.sally.tutorial.springboot2webapp.controller;

import com.sally.tutorial.springboot2webapp.model.Comment;
import com.sally.tutorial.springboot2webapp.model.CommentType;
import com.sally.tutorial.springboot2webapp.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Comment> allCommentsForToday = commentService.getAllCommentsForToday();
        Map<CommentType, List<Comment>> groupedComments = allCommentsForToday.stream().collect(Collectors.groupingBy());
        return "Comments";
    }
}
