package com.example.project.E.Pass.System.Backend.repository;

import com.example.project.E.Pass.System.Backend.entity.Pass;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassRepository extends MongoRepository<Pass, String> {

    Optional<Pass> findByUserId(String userId);
    
    List<Pass> findByUserIdAndStatus(String userId, com.example.project.E.Pass.System.Backend.enums.PassStatus status);
    
    List<Pass> findByStatus(com.example.project.E.Pass.System.Backend.enums.PassStatus status);
    
    boolean existsByUserIdAndStatus(String userId, com.example.project.E.Pass.System.Backend.enums.PassStatus status);
    
    boolean existsByUserIdAndStatusNotIn(String userId, List<com.example.project.E.Pass.System.Backend.enums.PassStatus> statuses);
}
