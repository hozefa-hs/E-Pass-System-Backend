package com.example.project.E.Pass.System.Backend.repository;

import com.example.project.E.Pass.System.Backend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

}
