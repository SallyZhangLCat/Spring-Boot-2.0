package com.sally.tutorial.springboot2webapp.service;

import com.sally.tutorial.springboot2webapp.exception.UsernameNotFoundException;
import com.sally.tutorial.springboot2webapp.model.User;
import com.sally.tutorial.springboot2webapp.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByUsername(String username) throws UsernameNotFoundException {
       User gotUser = userRepository.findByUsername(username);
       return gotUser;
    }


    @Transactional(rollbackFor = Exception.class)
    public User saveOne(User entity) {
        User savedUser = userRepository.save(entity);
        return savedUser;
    }
}
