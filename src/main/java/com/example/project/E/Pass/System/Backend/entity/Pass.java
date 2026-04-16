package com.example.project.E.Pass.System.Backend.entity;

import com.example.project.E.Pass.System.Backend.enums.PassDuration;
import com.example.project.E.Pass.System.Backend.enums.PassStatus;
import com.example.project.E.Pass.System.Backend.enums.PassType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Document(collection = "passes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pass {

    @Id
    private String id;
    
    private String userId;
    
    private PassType passType;
    
    private PassDuration duration;
    
    private PassStatus status;
    
    private LocalDateTime validFrom;
    
    private LocalDateTime validTill;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    public Pass(String userId, PassType passType, PassDuration duration, PassStatus status) {
        this.userId = userId;
        this.passType = passType;
        this.duration = duration;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }
    
    public boolean isActive() {
        return status == PassStatus.APPROVED 
            && validFrom != null 
            && validTill != null 
            && LocalDateTime.now().isAfter(validFrom) 
            && LocalDateTime.now().isBefore(validTill);
    }
    
    public boolean isPending() {
        return status == PassStatus.PENDING;
    }
    
    public boolean isRejected() {
        return status == PassStatus.REJECTED;
    }
}
