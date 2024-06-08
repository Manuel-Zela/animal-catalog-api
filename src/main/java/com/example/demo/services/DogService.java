package com.example.demo.services;

import com.example.demo.dto.DogDeleteRequest;
import com.example.demo.dto.DogWriteRequest;
import com.example.demo.dto.ServiceResponse;
import com.example.demo.model.Dog;
import com.example.demo.repositories.DogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DogService {

    private final DogRepository dogRepository;

    @Value("${dogs.images_path}")
    private String imagesPath;

    @Value("${dogs.images_url}")
    private String imagesUrl;

    public List<Dog> getDogs() {
        return (List<Dog>) dogRepository.findAll();
    }

    public ServiceResponse<?> addDog(DogWriteRequest request) {
        log.debug("Received addDog request: {}", request);

        if (request == null) {
            return ServiceResponse.error("Please provide a valid create product request!");
        }

        if (request.getName() == null || request.getName().isBlank()) {
            log.debug("Name is blank");
            return ServiceResponse.error("Name cannot be blank!");
        }

        if (request.getLifespan() == null || request.getLifespan().isBlank()) {
            log.debug("Lifespan is blank");
            return ServiceResponse.error("Lifespan cannot be empty!");
        }

        Dog dog = request.getId() != null ?
                dogRepository.findById(request.getId()).orElse(new Dog()) :
                new Dog();

        try {
            if (request.getImage() != null && !request.getImage().isEmpty()) {
                saveImage(request.getImage());
                dog.setImageName(request.getImage().getOriginalFilename());
                dog.setImagePath(imagesUrl + "/" + request.getImage().getOriginalFilename());
            }

            dog.setName(request.getName());
            dog.setDescription(request.getDescription());
            dog.setColors(request.getColors());
            dog.setTemperament(request.getTemperament());
            dog.setLifespan(request.getLifespan());
            dog.setBreed_group(request.getBreed_group());
            dog.setOrigin(request.getOrigin());
            dog.setSize(request.getSize());

            dogRepository.save(dog);

            return ServiceResponse.ok();
        } catch (Exception e) {
            log.error("Error during save", e);
            return ServiceResponse.error("Error during dog save!");
        }
    }

    public ServiceResponse<?> deleteDog(DogDeleteRequest request) {
        if (request == null) {
            return ServiceResponse.error("Please provide a valid delete request!");
        }

        if (request.getId() == null || request.getId().isBlank()) {
            return ServiceResponse.error("Id value should be provided!");
        }

        Optional<Dog> dogOptional = dogRepository.findById(request.getId());
        if (dogOptional.isPresent()) {
            dogRepository.deleteById(dogOptional.get().getId());
            return ServiceResponse.ok();
        } else {
            return ServiceResponse.error("Dog not present in database");
        }
    }

    private void saveImage(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(imagesPath).toAbsolutePath().normalize();
            Path filePath = path.resolve(file.getOriginalFilename()).normalize();

            File directory = path.toFile();
            if (!directory.exists()) {
                boolean mkdirs = directory.mkdirs();
                if (!mkdirs) {
                    log.error("Failed to create directory: {}", imagesPath);
                    throw new IOException("Failed to create directory for image storage");
                }
            }

            log.debug("Saving image to path: {}", filePath.toAbsolutePath());

            Files.deleteIfExists(filePath);
            Files.write(filePath, bytes);
        }
    }

    public List<Dog> searchDogByName(String name) {
        return (List<Dog>) dogRepository.findByName(name.toLowerCase());
    }

    public List<Dog> getDogsLimit(int limit) {
        return dogRepository.findAll(PageRequest.of(0, limit)).getContent();
    }
}
