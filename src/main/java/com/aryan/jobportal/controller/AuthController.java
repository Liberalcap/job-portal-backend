package com.aryan.jobportal.controller;

import com.aryan.jobportal.dto.AuthRequest;
import com.aryan.jobportal.dto.AuthResponse;
import com.aryan.jobportal.entity.User;
import com.aryan.jobportal.security.JwtService;
import com.aryan.jobportal.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService,
                          JwtService jwtService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ FIXED REGISTER (NOW RETURNS TOKEN)
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@RequestBody User user) {

        // Save user
        User savedUser = userService.registerUser(user);

        // Create UserDetails
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(savedUser.getEmail())
                .password(savedUser.getPassword())
                .authorities(savedUser.getRole())
                .build();

        // Generate JWT token
        String token = jwtService.generateToken(userDetails);

        // Return response with token
        return new AuthResponse(token, savedUser.getEmail(), savedUser.getRole());
    }

    // ✅ LOGIN (ALREADY CORRECT)
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {

        User user = userService.getUserByEmail(authRequest.getEmail());

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }

        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        // Create UserDetails
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole())
                .build();

        // Generate token
        String token = jwtService.generateToken(userDetails);

        // Return response
        return new AuthResponse(token, user.getEmail(), user.getRole());
    }
}