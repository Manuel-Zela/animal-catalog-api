package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dogs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Dog {
    @Id
    private String id;
    private String name;
    private String breed_group;
    private String size ;
    private String lifespan ;
    private String origin;
    private String temperament ;
    private String colors;
    private String description;
    private String image ;
}
