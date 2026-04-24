package com.example.project.E.Pass.System.Backend.dto;

import com.example.project.E.Pass.System.Backend.enums.PassDuration;
import com.example.project.E.Pass.System.Backend.enums.PassStatus;
import com.example.project.E.Pass.System.Backend.enums.PassType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResponseDto {
    private String passId;
    private boolean valid;
    private PassStatus status;
    private PassType passType;
    private PassDuration passDuration;
    private String message;
    private LocalDateTime validatedAt;
}
