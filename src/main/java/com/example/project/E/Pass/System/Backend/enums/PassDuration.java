package com.example.project.E.Pass.System.Backend.enums;

public enum PassDuration {
    ONE_MONTH(1),
    THREE_MONTHS(3),
    SIX_MONTHS(6);
    
    private final int months;
    
    PassDuration(int months) {
        this.months = months;
    }
    
    public int getMonths() {
        return months;
    }
    
    public static PassDuration fromString(String duration) {
        if (duration == null) {
            throw new IllegalArgumentException("Duration cannot be null");
        }
        
        switch (duration.toUpperCase()) {
            case "1":
            case "ONE_MONTH":
                return ONE_MONTH;
            case "3":
            case "THREE_MONTHS":
                return THREE_MONTHS;
            case "6":
            case "SIX_MONTHS":
                return SIX_MONTHS;
            default:
                throw new IllegalArgumentException("Invalid duration: " + duration);
        }
    }
}
