package com.example.demo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CatWriteRequest {
    private String id;
    private String name;
    private String origin;
    private String temperament ;
    private String colors;
    private String description;
    private MultipartFile image ;
}
