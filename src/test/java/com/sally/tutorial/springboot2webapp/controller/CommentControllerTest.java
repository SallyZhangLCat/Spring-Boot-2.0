package com.sally.tutorial.springboot2webapp.controller;

import com.sally.tutorial.springboot2webapp.model.Comment;
import com.sally.tutorial.springboot2webapp.model.CommentType;
import com.sally.tutorial.springboot2webapp.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
@RunWith(SpringRunner.class)
public class CommentControllerTest {
    @MockBean
    private CommentService commentServiceMock;
    @Autowired
    MockMvc mockMvc;

    @Test
    public void createComment_HappyPath_ShouldReturnStatus3xx() throws Exception {
        //when
        ResultActions resultActions = mockMvc.perform(post("/comment")
                .param("plusComment", "Test comment")
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(commentServiceMock, times(1)).saveAll(anyList());
        verifyNoMoreInteractions(commentServiceMock);
    }

    @Test
    public void getComment_HappyPath_ShouldReturnStatus2xx() throws Exception{
        //given
        Comment comment = new Comment();
        comment.setType(CommentType.PLUS);
        comment.setComment("Test Plus");
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        Comment comment1 = new Comment();
        comment1.setType(CommentType.STAR);
        comment1.setComment("Test Star");
        comment1.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        List<Comment> comments = Arrays.asList(comment, comment1);

        when(commentServiceMock.getAllCommentsForToday()).thenReturn(comments);

        //when
        ResultActions resultActions = mockMvc.perform(get("/"));

        //then
        resultActions
                //status code
                .andExpect(status().isOk())
                //the selected view
                .andExpect(view().name("comment"))
                //model
                .andExpect(model().attribute("plusComments", hasSize(1)))
                .andExpect(model().attribute("plusComments", hasItem(
                        allOf(
                                hasProperty("comment", is("Test Plus")),
                                hasProperty("type", is(CommentType.PLUS))
                        )
                )))
                .andExpect(model().attribute("starComments", hasSize(1)))
                .andExpect(model().attribute("starComments", hasItem(
                        allOf(
                                hasProperty("comment", is("Star Plus")),
                                hasProperty("type", is(CommentType.STAR))
                        )
                )));

        verify(commentServiceMock, times(1)).getAllCommentsForToday();
        verifyNoMoreInteractions(commentServiceMock);
    }
}
