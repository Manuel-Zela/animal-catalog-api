package com.example.demo.controller;

import com.example.demo.repositories.DogRepository;
import com.example.demo.model.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dog")
public class DogController {
    @Autowired
    private DogRepository dogRepository;

    @PostMapping("/create")
    public Dog createDog(@RequestBody Dog dog) {
        return dogRepository.save(dog);
    }

    @GetMapping
    public List<Dog> getAllDog() {
        return dogRepository.findAll();
    }

    @GetMapping("/{id}")
    public Dog getDogById(@PathVariable String id) {
        return dogRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Dog updateDog(@PathVariable String id, @RequestBody Dog dog) {
        dog.setId(id);
        return dogRepository.save(dog);
    }

    @DeleteMapping("/{id}")
    public void deleteDog(@PathVariable String id) {
        dogRepository.deleteById(id);
    }
}
