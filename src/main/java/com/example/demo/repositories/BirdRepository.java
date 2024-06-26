package com.example.demo.repositories;

import com.example.demo.model.Bird;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BirdRepository extends MongoRepository<Bird ,String> {
    @Query("{ 'name' : { $regex: ?0, $options: 'i' } }")
    List<Bird> findByName(String name);
}
