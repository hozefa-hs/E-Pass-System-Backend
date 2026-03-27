package com.example.project.E.Pass.System.Backend.dto.response;

import com.example.project.E.Pass.System.Backend.enums.Role;
import lombok.Data;

@Data
public class AuthResponse {
    
    private String token;
    private String type = "Bearer";
    private String userId;
    private String email;
    private Role role;
    
    public AuthResponse(String token, String userId, String email, Role role) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.role = role;
    }
}
