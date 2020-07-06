package com.sally.tutorial.springboot2webapp;

import com.sally.tutorial.springboot2webapp.model.Comment;
import com.sally.tutorial.springboot2webapp.model.CommentType;
import com.sally.tutorial.springboot2webapp.model.User;
import com.sally.tutorial.springboot2webapp.repo.CommentRepository;
import com.sally.tutorial.springboot2webapp.repo.UserRepository;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import javax.swing.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNot.*;
import static org.hamcrest.core.IsNull.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@DataJpaTest
class SpringBoot2WebAppApplicationTests {

	@Autowired
	private TestEntityManager testEntityManager;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void findByCreatedYearAndMonthAndDay_HappyPath_ShouldReturnOneComment() {
		//Given
		Comment comment = new Comment();
		comment.setComment("Test");
		comment.setType(CommentType.PLUS);
		comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		testEntityManager.persist(comment);
		testEntityManager.flush();

		//When
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int date = calendar.get(Calendar.DATE);
		List<Comment> comments = commentRepository.findByCreatedYearAndMonthAndDay(year, month, date);

		//Then
		assertThat(comments.size(), is(1));
		assertThat(comments.get(0).getComment(), is("Test"));
	}

	@Test
	public void save_HappyPath_ShouldSave1Comment(){
		//Given
		Comment comment = new Comment();
		comment.setComment("Test");
		comment.setType(CommentType.PLUS);
		comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));

		//When
		Comment saved = commentRepository.save(comment);//save the entity to the underlying database

		//Then
		assertThat(saved, is(comment));
	}

	@Test
	public void findByUsername_HappyPath_ShouldReturnOneUser() {
		//Given
		User user = new User();
		user.setPassword("888888");
		user.setUsername("Sally");
		user.setRole("USER");
		testEntityManager.persist(user);
		testEntityManager.flush();

		//When
		User actual = userRepository.findByUsername("Sally");

		//Then
		assertThat(actual, is(user));
	}

	@Test
	public void save_HappyPath_ShouldSave1User(){
		//Given
		User user = new User();
		user.setPassword("888888");
		user.setUsername("Sally");
		user.setRole("USER");


		//When
		User saved = userRepository.save(user);//save the entity to the underlying database
		User user3 =new User();

		//Then
		assertThat(saved, is(not(nullValue())));
		assertThat(saved.getId(), is(not(nullValue())));
	}

}
