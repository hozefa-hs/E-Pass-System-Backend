package com.example.project.E.Pass.System.Backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping("/health")
    public String healthCheck() {
        return "Your E-Pass System backend is now running";
    }
}
