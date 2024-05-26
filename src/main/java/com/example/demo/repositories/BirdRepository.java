package com.example.demo.repositories;

import com.example.demo.model.Bird;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BirdRepository extends MongoRepository<Bird ,String> {
    Bird findByName(String name);
}
