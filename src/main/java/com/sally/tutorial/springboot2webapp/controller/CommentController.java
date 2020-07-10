package com.sally.tutorial.springboot2webapp.controller;

import com.sally.tutorial.springboot2webapp.model.Comment;
import com.sally.tutorial.springboot2webapp.model.CommentType;
import com.sally.tutorial.springboot2webapp.model.User;
import com.sally.tutorial.springboot2webapp.service.CommentService;
import com.sally.tutorial.springboot2webapp.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Timestamp;
import java.util.*;
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
        //group the comments according to commentType
        Map<CommentType, List<Comment>> groupedComments = allCommentsForToday.stream().collect(Collectors.groupingBy(Comment::getType));
        //put comment groups into model
        model.addAttribute("plusComments", groupedComments.get(CommentType.PLUS));
        model.addAttribute("deltaComments", groupedComments.get(CommentType.DELTA));
        model.addAttribute("starComments", groupedComments.get(CommentType.STAR));
        return "Comments";
    }


    @PostMapping("/comment")
    public String createComment(String plusComment, String deltaComment, String starComment) {
        List<Comment> comments = new ArrayList<>();
        if (StringUtils.isNotEmpty(plusComment)) {//plusComment
            comments.add(getComment(plusComment, CommentType.PLUS));
        }
        if (StringUtils.isNotEmpty(deltaComment)) {//deltaComment
            comments.add(getComment(deltaComment, CommentType.DELTA));
        }
        if (StringUtils.isNotEmpty(starComment)) {//starComment
            comments.add(getComment(plusComment, CommentType.STAR));
        }
        commentService.saveAll(comments);
        return "redirect:/";//redirect to the index page
    }

    /**
     * Create a Comment object
     * @param commentType the type of the Comment to be created
     * @return the created Comment object
     */
    public Comment getComment(String comment, CommentType commentType) {
        Comment commentObject = new Comment();
        commentObject.setComment(comment);
        commentObject.setType(commentType);
        commentObject.setCreatedDate(new Timestamp(new Date().getTime()));
        return  commentObject;
    }
}
