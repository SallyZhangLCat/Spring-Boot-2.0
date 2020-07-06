package com.sally.tutorial.springboot2webapp.repo;

import com.sally.tutorial.springboot2webapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * find a user by username. This query method will be translated into the following query: SELECT u FROM User u WHERE u.username = 1?
     * @param username
     * @return the found user
     */
    public User findByUsername(String username);
}
