package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cats")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cat {
    @Id
    private String id;
    private String name;
    private String origin;
    private String temperament ;
    private String colors;
    private String description;
    private String image ;
}