package com.sally.tutorial.springboot2webapp.repo;

import com.sally.tutorial.springboot2webapp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c" +
            " WHERE year(c.createdDate) = ?1" +
            " AND month(c.createdDate) = ?2" +
            " AND day(c.createdDate) = ?3")
    public List<Comment> findByCreatedYearAndMonthAndDay(int year, int month, int day);
}
