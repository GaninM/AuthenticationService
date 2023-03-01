package com.example.authenticationservice.controller;

import com.example.authenticationservice.exception.RegistrationException;
import com.example.authenticationservice.model.Request.UserRequest;
import com.example.authenticationservice.model.User;
import com.example.authenticationservice.model.response.ErrorResponse;
import com.example.authenticationservice.model.response.TokenResponse;
import com.example.authenticationservice.service.TokenService;
import com.example.authenticationservice.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;

    public UserController(@Lazy UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping()
    public ResponseEntity<String> registration(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/token")
    public TokenResponse authenticate(@RequestBody UserRequest userRequest) {
        userService.checkCredentials(userRequest.getUserName(), userRequest.getUserPassword());

        return new TokenResponse(tokenService.generateToken(userRequest.getUserName()));
    }

    @ExceptionHandler({RegistrationException.class, LoginException.class})
    public ResponseEntity<ErrorResponse> handleUserRegistrationException(RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(ex.getMessage()));
    }
}

