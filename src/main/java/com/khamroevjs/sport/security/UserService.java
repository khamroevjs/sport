package com.khamroevjs.sport.security;

import com.khamroevjs.sport.dto.RegisterRequest;
import com.khamroevjs.sport.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    User registerUser(RegisterRequest request);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}

