package com.example.project.E.Pass.System.Backend.controller;

import com.example.project.E.Pass.System.Backend.dto.PassRequestDto;
import com.example.project.E.Pass.System.Backend.dto.PassResponseDto;
import com.example.project.E.Pass.System.Backend.service.PassService;
import com.example.project.E.Pass.System.Backend.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passes")
@RequiredArgsConstructor
public class PassController {

    private final PassService passService;
    private final JwtUtil jwtUtil;

    @PostMapping
    @PreAuthorize("hasRole('PASSENGER')")
    public ResponseEntity<PassResponseDto> applyForPass(
            @Valid @RequestBody PassRequestDto request,
            @RequestHeader("Authorization") String authHeader) {
        
        String userId = jwtUtil.getUserIdFromToken(authHeader.replace("Bearer ", ""));
        PassResponseDto response = passService.applyForPass(userId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-pass")
    @PreAuthorize("hasRole('PASSENGER')")
    public ResponseEntity<PassResponseDto> getMyPass(
            @RequestHeader("Authorization") String authHeader) {
        
        String userId = jwtUtil.getUserIdFromToken(authHeader.replace("Bearer ", ""));
        PassResponseDto response = passService.getPassByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{passId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PassResponseDto> approvePass(@PathVariable String passId) {
        PassResponseDto response = passService.approvePass(passId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{passId}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PassResponseDto> rejectPass(@PathVariable String passId) {
        PassResponseDto response = passService.rejectPass(passId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PassResponseDto>> getPendingPasses() {
        List<PassResponseDto> pendingPasses = passService.getAllPendingPasses();
        return ResponseEntity.ok(pendingPasses);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PassResponseDto>> getAllPasses() {
        List<PassResponseDto> allPasses = passService.getAllPasses();
        return ResponseEntity.ok(allPasses);
    }
}
