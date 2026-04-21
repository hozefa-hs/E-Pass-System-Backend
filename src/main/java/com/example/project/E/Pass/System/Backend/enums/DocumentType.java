package com.example.project.E.Pass.System.Backend.enums;

public enum DocumentType {
    COLLEGE_ID,
    AGE_PROOF,
    COMPANY_LETTER;
    
    public static DocumentType fromPassType(com.example.project.E.Pass.System.Backend.enums.PassType passType) {
        switch (passType) {
            case STUDENT:
                return COLLEGE_ID;
            case SENIOR_CITIZEN:
                return AGE_PROOF;
            case CORPORATE:
                return COMPANY_LETTER;
            default:
                throw new IllegalArgumentException("Invalid pass type: " + passType);
        }
    }
    
    public static DocumentType fromString(String documentType) {
        if (documentType == null) {
            throw new IllegalArgumentException("Document type cannot be null");
        }
        
        switch (documentType.toUpperCase()) {
            case "COLLEGE_ID":
                return COLLEGE_ID;
            case "AGE_PROOF":
                return AGE_PROOF;
            case "COMPANY_LETTER":
                return COMPANY_LETTER;
            default:
                throw new IllegalArgumentException("Invalid document type: " + documentType);
        }
    }
    
    public String getDisplayName() {
        switch (this) {
            case COLLEGE_ID:
                return "College ID";
            case AGE_PROOF:
                return "Age Proof";
            case COMPANY_LETTER:
                return "Company Letter";
        }
        return "";
    }
}
