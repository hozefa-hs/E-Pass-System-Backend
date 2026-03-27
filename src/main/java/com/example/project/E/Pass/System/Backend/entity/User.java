package com.example.project.E.Pass.System.Backend.entity;

import com.example.project.E.Pass.System.Backend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;
    
    @NonNull
    private String email;
    
    @NonNull
    private String password;
    
    @NonNull
    private Role role;
    
    private boolean isActive = true;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    private LocalDateTime lastLoginAt;
}
