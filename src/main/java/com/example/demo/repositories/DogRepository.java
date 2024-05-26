package com.example.demo.repositories;

import com.example.demo.model.Dog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogRepository extends MongoRepository<Dog, String> {
    Dog findByName(String name);
}
