package com.example.demo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DogWriteRequest {
    private String id;
    private String name;
    private String description;
    private String size;
    private String origin;
    private String breed_group;
    private String colors;
    private String lifespan;
    private String temperament;
    private MultipartFile image;
}

