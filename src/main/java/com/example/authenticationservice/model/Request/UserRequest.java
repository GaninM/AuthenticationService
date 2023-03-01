package com.example.authenticationservice.model.Request;

import lombok.Value;

@Value
public class UserRequest {
    String userName;
    String userPassword;
}
