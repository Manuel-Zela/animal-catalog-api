package com.example.demo.controller;

import com.example.demo.dto.DogDeleteRequest;
import com.example.demo.dto.DogWriteRequest;
import com.example.demo.dto.ServiceResponse;
import com.example.demo.repositories.DogRepository;
import com.example.demo.model.Dog;
import com.example.demo.services.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/dog")
public class DogController {

    @Autowired
    private DogService dogService;


    @GetMapping()
    public List<Dog> getAllDog() {
        return dogService.getDogs();
    }

    @PostMapping("/update")
    public ServiceResponse<?> addDog(@ModelAttribute DogWriteRequest request) {
        return dogService.addDog(request);
    }

    @DeleteMapping("/delete")
    public ServiceResponse<?> deleteProduct(@RequestBody DogDeleteRequest request) {
        ServiceResponse<?> response = dogService.deleteDog(request);
        return ServiceResponse.ok(response);
    }

    @GetMapping("/search/{name}")
    public List<Dog> searchDogsByName(@PathVariable String name) {
        return dogService.searchDogByName(name);
    }

    @GetMapping("/limit")
    public List<Dog> getDogsLimit(@RequestParam(defaultValue = "10") int limit) {
        return dogService.getDogsLimit(limit);
    }
}