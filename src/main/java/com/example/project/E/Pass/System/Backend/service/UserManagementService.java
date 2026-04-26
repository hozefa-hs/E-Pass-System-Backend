package com.example.project.E.Pass.System.Backend.service;

import com.example.project.E.Pass.System.Backend.dto.UserManagementDto;
import com.example.project.E.Pass.System.Backend.entity.User;
import com.example.project.E.Pass.System.Backend.enums.Role;
import com.example.project.E.Pass.System.Backend.exception.UserNotFoundException;
import com.example.project.E.Pass.System.Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserManagementService {

    private final UserRepository userRepository;

    public List<UserManagementDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UserManagementDto updateUserRole(String userId, String newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        try {
            Role role = Role.valueOf(newRole.toUpperCase());
            user.setRole(role);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + newRole);
        }

        User updatedUser = userRepository.save(user);
        return convertToDto(updatedUser);
    }

    public UserManagementDto updateUserStatus(String userId, boolean isActive) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        user.setActive(isActive);
        User updatedUser = userRepository.save(user);
        return convertToDto(updatedUser);
    }

    public void deleteUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found: " + userId);
        }
        userRepository.deleteById(userId);
    }

    private UserManagementDto convertToDto(User user) {
        return UserManagementDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .isActive(user.isActive())
                .createdAt(user.getCreatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }
}
