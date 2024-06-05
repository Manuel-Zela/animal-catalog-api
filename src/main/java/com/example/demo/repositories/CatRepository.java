package com.example.demo.repositories;

import com.example.demo.model.Cat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CatRepository extends MongoRepository<Cat , String> {
    @Query("{ 'name' : { $regex: ?0, $options: 'i' } }")
    List<Cat> findByName(String name);
}
