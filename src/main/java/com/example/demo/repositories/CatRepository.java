package com.example.demo.repositories;

import com.example.demo.model.Cat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CatRepository extends MongoRepository<Cat , String> {
    Cat findByName(String name);
}
