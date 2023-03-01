package com.example.authenticationservice.service.impl;

import com.example.authenticationservice.exception.RegistrationException;
import com.example.authenticationservice.model.Role;
import com.example.authenticationservice.model.User;
import com.example.authenticationservice.repository.RoleRepository;
import com.example.authenticationservice.repository.UserRepository;
import com.example.authenticationservice.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.*;

@Service
@Lazy
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void checkCredentials(String userName, String userPassword) {
        User user = userRepository.findByUsername(userName);
        if (user == null) try {
            throw new LoginException("Client with username: " + userName + " not found");
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }

        if (!BCrypt.checkpw(userPassword, user.getUserPassword())) try {
            throw new LoginException("Secret is incorrect");
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(User user) {
        if (user.equals(userRepository.findByUsername(user.getUsername()))) {
            throw new RegistrationException("Client with id: " + user.getUserPassword() + " already registered");
        }
        user.setUserPassword(BCrypt.hashpw(user.getUserPassword(), BCrypt.gensalt()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.getReferenceById(1L));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
