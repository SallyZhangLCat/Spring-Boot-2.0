package com.sally.tutorial.springboot2webapp.controller;

import com.sally.tutorial.springboot2webapp.service.CommentService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {
    @Autowired
    private CommentService commentService;
    @Autowired
    MockMvc mockMvc;

    @Test
    public void createComment_HappyPath_ShouldReturnStatus302() {
        mockMvc.perform()
    }
}
