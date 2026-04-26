package com.example.project.E.Pass.System.Backend.dto;

import com.example.project.E.Pass.System.Backend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserManagementDto {
    private String id;
    private String email;
    private Role role;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
}
