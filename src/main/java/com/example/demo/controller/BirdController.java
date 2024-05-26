package com.example.demo.controller;

import com.example.demo.model.Bird;
import com.example.demo.repositories.BirdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/birds")
public class BirdController {
    @Autowired
    private BirdRepository birdRepository;

    @GetMapping("")
    public List<Bird> getAllBird(){return birdRepository.findAll();}

    @PostMapping("/create")
    public Bird createBird(@RequestBody Bird bird){
        return  birdRepository.save(bird);
    }

    @GetMapping("{id}")
    public Bird getBirdById(@PathVariable String  id){
        return birdRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Bird updateBird(@PathVariable String id, @RequestBody Bird bird) {
        bird.setId(id);
        return birdRepository.save(bird);
    }

    @DeleteMapping("/{id}")
    public void deleteBird(@PathVariable String id) {
        birdRepository.deleteById(id);
    }
}