package com.sally.tutorial.springboot2webapp;

import com.sally.tutorial.springboot2webapp.model.User;
import com.sally.tutorial.springboot2webapp.repo.UserRepository;
import com.sally.tutorial.springboot2webapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {
    @MockBean
    UserRepository userRepository;

    UserService userService;

    @BeforeEach
    public void init(){
        userService = new UserService(userRepository);
    }

    @Test
    public void findByUsername_HappyPath_shouldReturnOneUser(){
        // Given
        User user = new User();
        user.setUsername("shazin");
        user.setPassword("sha908");
        user.setRole("USER");

        when(userRepository.findByUsername("Sally")).thenReturn(user);

        //when
        User actualUser = userService.findUserByUsername("Sally");

        //then
        User mockUser = userRepository.findByUsername("Sally");
        verify(userRepository, times(2)).findByUsername("Sally");
        assertThat(user, is(actualUser));
    }
}
