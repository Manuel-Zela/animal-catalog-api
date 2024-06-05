package com.example.demo.repositories;

import com.example.demo.model.Bird;
import com.example.demo.model.Dog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BirdRepository extends MongoRepository<Bird ,String> {
    @Query("{ 'name' : { $regex: ?0, $options: 'i' } }")
    List<Bird> findByName(String name);
}
