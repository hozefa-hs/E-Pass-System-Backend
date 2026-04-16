package com.example.project.E.Pass.System.Backend.dto;

import com.example.project.E.Pass.System.Backend.enums.PassDuration;
import com.example.project.E.Pass.System.Backend.enums.PassType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PassRequestDto {
    
    @NotNull(message = "Pass type is required")
    private PassType passType;
    
    @NotNull(message = "Duration is required")
    private PassDuration duration;
}
