package com.example.demo.controller;


import com.example.demo.dto.*;
import com.example.demo.model.Cat;
import com.example.demo.model.Dog;
import com.example.demo.services.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/cats")
public class CatController {
 @Autowired
 private CatService catService;

    @GetMapping("")
    public List<Cat> getAllCat() {
        return catService.getCat();
    }

    @PostMapping("/update")
    public ServiceResponse<?> addCat(@ModelAttribute CatWriteRequest request) {
        return catService.addCat(request);
    }

    @DeleteMapping("/delete")
    public ServiceResponse<?> deleteCats(@RequestBody CatDeleteRequest request) {
        ServiceResponse<?> response = catService.deleteCat(request);
        return ServiceResponse.ok(response);
    }

    @GetMapping("/search/{name}")
    public List<Cat> searchCatsByName(@PathVariable String name) {
        return catService.searchCatByName(name);
    }

    @GetMapping("/limit")
    public List<Cat> getCatLimit(@RequestParam(defaultValue = "10") int limit) {
        return catService.getCatLimit(limit);
    }
}


