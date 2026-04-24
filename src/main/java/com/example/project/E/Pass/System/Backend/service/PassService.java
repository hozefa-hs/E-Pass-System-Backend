package com.example.project.E.Pass.System.Backend.service;

import com.example.project.E.Pass.System.Backend.dto.PassRequestDto;
import com.example.project.E.Pass.System.Backend.dto.PassResponseDto;
import com.example.project.E.Pass.System.Backend.dto.ValidationResponseDto;
import com.example.project.E.Pass.System.Backend.entity.Pass;
import com.example.project.E.Pass.System.Backend.enums.PassDuration;
import com.example.project.E.Pass.System.Backend.enums.PassStatus;
import com.example.project.E.Pass.System.Backend.enums.PassType;
import com.example.project.E.Pass.System.Backend.exception.PassAlreadyExistsException;
import com.example.project.E.Pass.System.Backend.exception.PassNotFoundException;
import com.example.project.E.Pass.System.Backend.repository.PassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PassService {

    private final PassRepository passRepository;

    public PassResponseDto applyForPass(String userId, PassRequestDto request) {
        // Check if user already has an active or pending pass
        if (passRepository.existsByUserIdAndStatusNotIn(userId, 
                List.of(PassStatus.REJECTED))) {
            throw new PassAlreadyExistsException(
                "User already has an active or pending pass application");
        }

        // Create new pass with PENDING status
        Pass pass = new Pass(userId, request.getPassType(), 
                request.getDuration(), PassStatus.PENDING);
        
        Pass savedPass = passRepository.save(pass);
        return PassResponseDto.fromEntity(savedPass);
    }

    public PassResponseDto approvePass(String passId) {
        Pass pass = passRepository.findById(passId)
                .orElseThrow(() -> new PassNotFoundException("Pass not found: " + passId));

        if (pass.getStatus() != PassStatus.PENDING) {
            throw new IllegalStateException("Only pending passes can be approved");
        }

        // Update status and set validity dates
        pass.setStatus(PassStatus.APPROVED);
        pass.setValidFrom(LocalDateTime.now());
        
        // Calculate validTill based on duration
        LocalDateTime validTill = LocalDateTime.now().plusMonths(pass.getDuration().getMonths());
        pass.setValidTill(validTill);

        Pass updatedPass = passRepository.save(pass);
        return PassResponseDto.fromEntity(updatedPass);
    }

    public PassResponseDto rejectPass(String passId) {
        Pass pass = passRepository.findById(passId)
                .orElseThrow(() -> new PassNotFoundException("Pass not found: " + passId));

        if (pass.getStatus() != PassStatus.PENDING) {
            throw new IllegalStateException("Only pending passes can be rejected");
        }

        pass.setStatus(PassStatus.REJECTED);
        pass.setValidFrom(null);
        pass.setValidTill(null);

        Pass updatedPass = passRepository.save(pass);
        return PassResponseDto.fromEntity(updatedPass);
    }

    public PassResponseDto getPassByUserId(String userId) {
        return passRepository.findByUserId(userId)
                .map(PassResponseDto::fromEntity)
                .orElseThrow(() -> new PassNotFoundException("No pass found for user: " + userId));
    }

    public List<PassResponseDto> getAllPendingPasses() {
        return passRepository.findByStatus(PassStatus.PENDING).stream()
                .map(PassResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<PassResponseDto> getAllPasses() {
        return passRepository.findAll().stream()
                .map(PassResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public boolean hasActiveOrPendingPass(String userId) {
        return passRepository.existsByUserIdAndStatusNotIn(userId, 
                List.of(PassStatus.REJECTED));
    }

    public ValidationResponseDto validatePass(String passId) {
        Pass pass = passRepository.findById(passId)
                .orElseThrow(() -> new PassNotFoundException("Pass not found: " + passId));

        LocalDateTime now = LocalDateTime.now();
        boolean isValid = false;
        String message = "";

        // Case 1: Valid Pass
        if (pass.getStatus() == PassStatus.APPROVED &&
            pass.getValidFrom() != null &&
            pass.getValidTill() != null &&
            !now.isBefore(pass.getValidFrom()) &&
            !now.isAfter(pass.getValidTill())) {
            isValid = true;
            message = "PASS VALID - Allow Travel";
        }
        // Case 2: Pending Pass
        else if (pass.getStatus() == PassStatus.PENDING) {
            isValid = false;
            message = "PASS PENDING - Deny Travel";
        }
        // Case 3: Expired/Invalid Pass
        else {
            isValid = false;
            if (pass.getStatus() == PassStatus.REJECTED) {
                message = "PASS REJECTED - Deny Travel";
            } else if (pass.getValidFrom() != null && now.isBefore(pass.getValidFrom())) {
                message = "PASS NOT YET VALID - Deny Travel";
            } else if (pass.getValidTill() != null && now.isAfter(pass.getValidTill())) {
                message = "PASS EXPIRED - Deny Travel";
            } else {
                message = "PASS INVALID - Deny Travel";
            }
        }

        return ValidationResponseDto.builder()
                .passId(passId)
                .valid(isValid)
                .status(pass.getStatus())
                .passType(pass.getPassType())
                .passDuration(pass.getDuration())
                .message(message)
                .validatedAt(now)
                .build();
    }
}
