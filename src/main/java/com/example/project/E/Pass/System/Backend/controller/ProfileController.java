package com.example.project.E.Pass.System.Backend.controller;

import com.example.project.E.Pass.System.Backend.dto.ProfileDto;
import com.example.project.E.Pass.System.Backend.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileDto> getProfile(Authentication authentication) {
        ProfileDto profile = profileService.getProfile(authentication);
        return ResponseEntity.ok(profile);
    }
}
