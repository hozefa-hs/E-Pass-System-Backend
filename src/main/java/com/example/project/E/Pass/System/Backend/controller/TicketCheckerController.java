package com.example.project.E.Pass.System.Backend.controller;

import com.example.project.E.Pass.System.Backend.dto.ValidationResponseDto;
import com.example.project.E.Pass.System.Backend.exception.PassNotFoundException;
import com.example.project.E.Pass.System.Backend.service.PassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/ticket-checker")
@RequiredArgsConstructor
public class TicketCheckerController {

    private final PassService passService;

    @GetMapping("/validate/{passId}")
    @PreAuthorize("hasRole('TICKET_CHECKER')")
    public ResponseEntity<ValidationResponseDto> validatePass(@PathVariable String passId) {
        try {
            ValidationResponseDto response = passService.validatePass(passId);
            return ResponseEntity.ok(response);
        } catch (PassNotFoundException e) {
            ValidationResponseDto response = ValidationResponseDto.builder()
                    .passId(passId)
                    .valid(false)
                    .status(null)
                    .passType(null)
                    .passDuration(null)
                    .message("Pass not found")
                    .validatedAt(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ValidationResponseDto response = ValidationResponseDto.builder()
                    .passId(passId)
                    .valid(false)
                    .status(null)
                    .passType(null)
                    .passDuration(null)
                    .message("Error validating pass: " + e.getMessage())
                    .validatedAt(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(response);
        }
    }
}
