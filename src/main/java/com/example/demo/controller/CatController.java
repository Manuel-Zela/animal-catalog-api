package com.example.demo.controller;

import com.example.demo.model.Cat;
import com.example.demo.repositories.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cats")
public class CatController {
    @Autowired
    private CatRepository catRepository;

    @GetMapping("")
    public List<Cat> getAllCats(){return catRepository.findAll();}

    @PostMapping("/create")
    public Cat createCat(@RequestBody Cat cat){
        return  catRepository.save(cat);
    }

    @GetMapping("{id}")
    public Cat getCatById(@PathVariable String  id){
        return catRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Cat updateCat(@PathVariable String id, @RequestBody Cat cat) {
        cat.setId(id);
        return catRepository.save(cat);
    }

    @DeleteMapping("/{id}")
    public void deleteCat(@PathVariable String id) {
        catRepository.deleteById(id);
    }
}
