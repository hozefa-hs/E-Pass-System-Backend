package com.example.project.E.Pass.System.Backend.enums;

public enum PassType {
    STUDENT,
    SENIOR_CITIZEN,
    CORPORATE;
    
    public static PassType fromString(String passType) {
        if (passType == null) {
            throw new IllegalArgumentException("Pass type cannot be null");
        }
        
        switch (passType.toUpperCase()) {
            case "STUDENT":
                return STUDENT;
            case "SENIOR_CITIZEN":
                return SENIOR_CITIZEN;
            case "CORPORATE":
                return CORPORATE;
            default:
                throw new IllegalArgumentException("Invalid pass type: " + passType);
        }
    }
}
