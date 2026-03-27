package com.example.project.E.Pass.System.Backend.service;

import com.example.project.E.Pass.System.Backend.dto.response.AuthResponse;
import com.example.project.E.Pass.System.Backend.dto.request.LoginRequest;
import com.example.project.E.Pass.System.Backend.dto.request.RegisterRequest;
import com.example.project.E.Pass.System.Backend.entity.User;
import com.example.project.E.Pass.System.Backend.enums.Role;
import com.example.project.E.Pass.System.Backend.exception.AuthenticationException;
import com.example.project.E.Pass.System.Backend.exception.UserAlreadyExistsException;
import com.example.project.E.Pass.System.Backend.repository.UserRepository;
import com.example.project.E.Pass.System.Backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered: " + request.getEmail());
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(Objects.requireNonNull(passwordEncoder.encode(request.getPassword())));
        user.setRole(request.getRole());
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        String token = jwtUtil.generateToken(savedUser.getId(), savedUser.getEmail(), savedUser.getRole());

        return new AuthResponse(token, savedUser.getId(), savedUser.getEmail(), savedUser.getRole());
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmailAndIsActive(request.getEmail(), true)
                .orElseThrow(() -> new AuthenticationException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Invalid email or password");
        }

        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());

        return new AuthResponse(token, user.getId(), user.getEmail(), user.getRole());
    }

    public boolean validateToken(String token) {
        if (!jwtUtil.validateToken(token)) {
            return false;
        }

        String userId = jwtUtil.getUserIdFromToken(token);
        return userRepository.existsById(userId);
    }

    public User getCurrentUser(String token) {
        String userId = jwtUtil.getUserIdFromToken(token);
        return userRepository.findById(userId)
                .orElseThrow(() -> new AuthenticationException("User not found"));
    }

    public Role getCurrentUserRole(String token) {
        return jwtUtil.getRoleFromToken(token);
    }
}
