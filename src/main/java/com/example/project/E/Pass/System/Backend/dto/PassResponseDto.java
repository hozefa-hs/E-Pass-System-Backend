package com.example.project.E.Pass.System.Backend.dto;

import com.example.project.E.Pass.System.Backend.enums.PassDuration;
import com.example.project.E.Pass.System.Backend.enums.PassStatus;
import com.example.project.E.Pass.System.Backend.enums.PassType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PassResponseDto {
    
    private String id;
    private String userId;
    private PassType passType;
    private PassDuration duration;
    private PassStatus status;
    private LocalDateTime validFrom;
    private LocalDateTime validTill;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;
    
    public static PassResponseDto fromEntity(com.example.project.E.Pass.System.Backend.entity.Pass pass) {
        return new PassResponseDto(
            pass.getId(),
            pass.getUserId(),
            pass.getPassType(),
            pass.getDuration(),
            pass.getStatus(),
            pass.getValidFrom(),
            pass.getValidTill(),
            pass.getCreatedAt(),
            pass.getUpdatedAt(),
            pass.isActive()
        );
    }
}
