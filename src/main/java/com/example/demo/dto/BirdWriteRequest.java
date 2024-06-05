package com.example.demo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BirdWriteRequest {
    private String id;
    private String name;
    private String species;
    private String family ;
    private String habitat;
    private String place_of_found;
    private String diet ;
    private String description ;
    private String weight_kg;
    private String height_cm;
    private MultipartFile image ;
}
