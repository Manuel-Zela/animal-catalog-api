package com.example.demo.repositories;

import com.example.demo.model.Dog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogRepository extends MongoRepository<Dog, String> {
    @Query("{ 'name' : { $regex: ?0, $options: 'i' } }")
    List<Dog> findByName(String name);
}
