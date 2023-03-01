package com.example.authenticationservice.service;

import com.example.authenticationservice.model.User;

public interface UserService {


    void checkCredentials(String userName, String userPassword);

    void save(User user);

    User findByUsername(String username);
}
