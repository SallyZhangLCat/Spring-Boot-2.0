package com.sally.tutorial.springboot2webapp.exception;

public class UsernameNotFoundException extends RuntimeException {


    public UsernameNotFoundException() {
        super("The given username is not found.");
    }
}
