package com.khamroevjs.sport.controller;

import com.khamroevjs.sport.dto.LoginRequest;
import com.khamroevjs.sport.dto.AuthResponse;
import com.khamroevjs.sport.dto.RegisterRequest;
import com.khamroevjs.sport.model.User;
import com.khamroevjs.sport.model.UserRole;
import com.khamroevjs.sport.security.JwtUtil;
import com.khamroevjs.sport.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        if (userService.existsByUsername(request.username())) {
            return ResponseEntity.badRequest().body(new AuthResponse(null, "Username already exists"));
        }
        User user = userService.registerUser(request);
        String token = jwtUtil.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token, "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token, "Login successful"));
    }

    @GetMapping("/oauth2/success")
    public ResponseEntity<AuthResponse> oauth2Success(@AuthenticationPrincipal OAuth2User oauth2User) {
        String username = oauth2User.getAttribute("login");
        User user;
        Optional<User> optionalUser = userService.findByUsername(username);
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            String password = UUID.randomUUID().toString();
            user = userService.registerUser(new RegisterRequest(username, password, UserRole.USER));
        }
        String token = jwtUtil.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token, "Login successful"));
    }

    @GetMapping("/oauth2/failure")
    public ResponseEntity<AuthResponse> oauth2Failure() {
        return ResponseEntity.badRequest().body(new AuthResponse(null, "Login failed"));
    }
}
