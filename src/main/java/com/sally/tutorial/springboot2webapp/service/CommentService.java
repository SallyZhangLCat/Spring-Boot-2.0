package com.sally.tutorial.springboot2webapp.service;

import com.sally.tutorial.springboot2webapp.model.Comment;
import com.sally.tutorial.springboot2webapp.repo.CommentRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

@Service
public class CommentService {
    CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * Save a list of comment to underlying database
     * @return the list of saved comment
     */
    @org.springframework.transaction.annotation.Transactional(rollbackFor = Exception.class)
    public List<Comment> saveAll(List<Comment> entities) {
        List<Comment> savedEntities = commentRepository.saveAll(entities);
        return savedEntities;
    }

    /**
     * get a list of comments posted today
     * @return the list of comments posted today
     */
    public List<Comment> getAllCommentsForToday() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);
        List<Comment> gotComments = commentRepository.findByCreatedYearAndMonthAndDay(year, month, date);
        return gotComments;
    }
}
