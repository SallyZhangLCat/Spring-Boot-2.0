package com.sally.tutorial.springboot2webapp.repo;

import com.sally.tutorial.springboot2webapp.model.Comment;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class RepoConfig {

}
