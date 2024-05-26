package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "birds")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Bird {
    @Id
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
    private String image ;
}