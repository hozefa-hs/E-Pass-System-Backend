package com.example.project.E.Pass.System.Backend.enums;

public enum PassStatus {
    PENDING,
    APPROVED,
    REJECTED;
    
    public static PassStatus fromString(String status) {
        if (status == null) {
            throw new IllegalArgumentException("Pass status cannot be null");
        }
        
        switch (status.toUpperCase()) {
            case "PENDING":
                return PENDING;
            case "APPROVED":
                return APPROVED;
            case "REJECTED":
                return REJECTED;
            default:
                throw new IllegalArgumentException("Invalid pass status: " + status);
        }
    }
}
