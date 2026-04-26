package com.example.project.E.Pass.System.Backend.controller;

import com.example.project.E.Pass.System.Backend.dto.UserManagementDto;
import com.example.project.E.Pass.System.Backend.entity.User;
import com.example.project.E.Pass.System.Backend.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserManagementController {

    private final UserManagementService userManagementService;

    @GetMapping
    public ResponseEntity<List<UserManagementDto>> getAllUsers() {
        List<UserManagementDto> users = userManagementService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<UserManagementDto> updateUserRole(
            @PathVariable String userId,
            @RequestParam String newRole) {
        UserManagementDto updatedUser = userManagementService.updateUserRole(userId, newRole);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/{userId}/status")
    public ResponseEntity<UserManagementDto> updateUserStatus(
            @PathVariable String userId,
            @RequestParam boolean isActive) {
        UserManagementDto updatedUser = userManagementService.updateUserStatus(userId, isActive);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userManagementService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
