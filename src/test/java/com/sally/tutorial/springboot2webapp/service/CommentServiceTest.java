package com.sally.tutorial.springboot2webapp.service;

import com.sally.tutorial.springboot2webapp.model.Comment;
import com.sally.tutorial.springboot2webapp.model.CommentType;
import com.sally.tutorial.springboot2webapp.repo.CommentRepository;
import com.sally.tutorial.springboot2webapp.service.CommentService;
import static org.hamcrest.MatcherAssert.*;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.core.Is.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Test if CommentService works well and if mockbean will replace other existing bean
 */
@SpringBootTest
//@RunWith(SpringRunner.class)
public class CommentServiceTest {

    @MockBean
    private CommentRepository commentRepositoryMock;

    @Autowired
    private CommentRepository commentRepositoryActual;

    private CommentService commentServiceMock;

    private CommentService commentServiceActual;

    @BeforeEach
    public void init(){
        commentServiceMock = new CommentService(commentRepositoryMock);
        commentServiceActual = new CommentService(commentRepositoryActual);
    }


    /**
     * Test getAllCommentsForToday method
     */
    @Test
    public void findByCreatedYearAndMonthAndDay_HappyPath_ShouldReturnOneComment() {
        //given
        Comment comment = new Comment();
        comment.setType(CommentType.PLUS);
        comment.setComment("Test");
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        List<Comment> comments = Arrays.asList(comment);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);

        when(commentRepositoryMock.findByCreatedYearAndMonthAndDay(year, month, date)).thenReturn(comments);

        //when
        List<Comment> actualComments = commentServiceActual.getAllCommentsForToday();

        //then
        List<Comment> mockComments = commentRepositoryMock.findByCreatedYearAndMonthAndDay(year, month, date);
        verify(commentRepositoryMock, times(2)).findByCreatedYearAndMonthAndDay(year, month, date);
        assertThat(actualComments, is(mockComments));
    }

    /**
     * Test saveAll method
     */
    @Test
    public void findByCreatedYearAndMonthAndDay_HappyPath_ShouldSave2Comments() {
        //given
        Comment comment = new Comment();
        comment.setComment("Test Plus");
        comment.setType(CommentType.PLUS);
        comment.setCreatedBy("Shazin");
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        Comment comment2 = new Comment();
        comment2.setComment("Test Star");
        comment2.setType(CommentType.STAR);
        comment2.setCreatedBy("Shahim");
        comment2.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        List<Comment> comments = Arrays.asList(comment, comment2);
        when(commentRepositoryMock.saveAll(comments)).thenReturn(comments);

        //when
        List<Comment> actualComments = commentServiceActual.saveAll(comments);

        //then
        List<Comment> mockComments = commentRepositoryMock.saveAll(comments);
        verify(commentRepositoryMock, times(2)).saveAll(comments);
        assertThat(actualComments, is(mockComments));
    }
}
