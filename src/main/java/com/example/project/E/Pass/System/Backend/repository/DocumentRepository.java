package com.example.project.E.Pass.System.Backend.repository;

import com.example.project.E.Pass.System.Backend.entity.DocumentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends MongoRepository<DocumentEntity, String> {

    Optional<DocumentEntity> findByPassId(String passId);
    
    Optional<DocumentEntity> findByGridFsId(String gridFsId);
    
    boolean existsByPassId(String passId);
}
