package com.sally.tutorial.springboot2webapp.controller;

import com.sally.tutorial.springboot2webapp.model.Comment;
import com.sally.tutorial.springboot2webapp.model.CommentType;
import com.sally.tutorial.springboot2webapp.model.User;
import com.sally.tutorial.springboot2webapp.service.CommentService;
import com.sally.tutorial.springboot2webapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
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
        //anonymous class
        Map<CommentType, List<Comment>> groupedComments2 = allCommentsForToday.stream().collect(Collectors.groupingBy(new Function<Comment, CommentType>() {
            public CommentType apply(Comment comment){
                return comment.getType();
            }
        }));
        //lambda expression
        Map<CommentType, List<Comment>> groupedComments3 = allCommentsForToday.stream().collect(Collectors.groupingBy((comment) -> comment.getType()));
        //method reference
        Map<CommentType, List<Comment>> groupedComments = allCommentsForToday.stream().collect(Collectors.groupingBy(Comment::getType));
        return "Comments";
    }
}
