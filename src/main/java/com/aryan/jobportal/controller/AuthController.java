package com.aryan.jobportal.controller;

import com.aryan.jobportal.dto.AuthRequest;
import com.aryan.jobportal.dto.AuthResponse;
import com.aryan.jobportal.entity.PasswordResetToken;
import com.aryan.jobportal.entity.User;
import com.aryan.jobportal.repository.PasswordResetTokenRepository;
import com.aryan.jobportal.repository.UserRepository;
import com.aryan.jobportal.security.JwtService;
import com.aryan.jobportal.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "https://job-portal-frontend-six-pi.vercel.app")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;

    public AuthController(UserService userService,
                          JwtService jwtService,
                          PasswordEncoder passwordEncoder,
                          PasswordResetTokenRepository passwordResetTokenRepository,
                          UserRepository userRepository) {

        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userRepository = userRepository;
    }

    // ✅ REGISTER
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

        // Return response
        return new AuthResponse(token, savedUser.getEmail(), savedUser.getRole());
    }

    // ✅ LOGIN
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

    // ✅ FORGOT PASSWORD
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody Map<String, String> request) {

        String email = request.get("email");

        Optional<User> userOptional = userRepository.findByEmail(email);

        // Security purpose
        if (userOptional.isEmpty()) {
            return "If this email exists, a reset link has been sent.";
        }

        User user = userOptional.get();

        // Generate token
        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();

        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));

        passwordResetTokenRepository.save(resetToken);

        // Simulate email
        System.out.println(
                "Reset Link: https://job-portal-frontend-six-pi.vercel.app/reset-password/" + token
        );

        return "Reset link sent successfully.";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody Map<String, String> request) {

        String token = request.get("token");

        // ✅ FIXED
        String newPassword = request.get("newPassword");

        Optional<PasswordResetToken> tokenOptional =
                passwordResetTokenRepository.findByToken(token);

        // Invalid token
        if (tokenOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid token"
            );
        }

        PasswordResetToken resetToken = tokenOptional.get();

        // Expired token
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Token expired"
            );
        }

        User user = resetToken.getUser();

        // ✅ Encode new password
        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);

        // ✅ Delete token after successful reset
        passwordResetTokenRepository.delete(resetToken);

        return "Password reset successful";
    }
}