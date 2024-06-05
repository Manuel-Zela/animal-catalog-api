package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.model.Bird;
import com.example.demo.services.BirdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/birds")
public class BirdController {
    @Autowired
    private BirdService birdService;

    @GetMapping()
    public List<Bird> getAllBird() {
        return birdService.getBird();
    }

    @PostMapping("/update")
    public ServiceResponse<?> addBird(@ModelAttribute BirdWriteRequest request) {
        return birdService.addBird(request);
    }

    @DeleteMapping("/delete")
    public ServiceResponse<?> deleteBird(@RequestBody BirdDeleteRequest request) {
        ServiceResponse<?> response = birdService.deleteBird(request);
        return ServiceResponse.ok(response);
    }

    @GetMapping("/search/{name}")
    public List<Bird> searchCatsByName(@PathVariable String name) {
        return birdService.searchBirdByName(name);
    }
}


