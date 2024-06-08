package com.example.demo.services;

import com.example.demo.dto.*;
import com.example.demo.model.Bird;
import com.example.demo.model.Dog;
import com.example.demo.repositories.BirdRepository;
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
public class BirdService {

    private final BirdRepository birdRepository;

    @Value("${birds.images_path}")
    private String imagesPath;

    @Value("${birds.images_url}")
    private String imagesUrl;

    public List<Bird> getBird() {
        return (List<Bird>) birdRepository.findAll();
    }

    public ServiceResponse<?> addBird(BirdWriteRequest request) {
        log.debug("Received addBird request: {}", request);

        if (request == null) {
            return ServiceResponse.error("Please provide a valid create product request!");
        }

        if (request.getName() == null || request.getName().isBlank()) {
            log.debug("Name is blank");
            return ServiceResponse.error("Name cannot be blank!");
        }

        if (request.getHabitat() == null || request.getHabitat().isBlank()) {
            log.debug("Habitat is blank");
            return ServiceResponse.error("Habitat cannot be empty!");
        }

        Bird bird = request.getId() != null ?
                birdRepository.findById(request.getId()).orElse(new Bird()) :
                new Bird();

        try {
            if (request.getImage() != null && !request.getImage().isEmpty()) {
                saveImage(request.getImage());
                bird.setImageName(request.getImage().getOriginalFilename());
                bird.setImagePath(imagesUrl + "/" + request.getImage().getOriginalFilename());
            }

            bird.setName(request.getName());
            bird.setDescription(request.getDescription());
            bird.setDiet(request.getDiet());
            bird.setFamily(request.getFamily());
            bird.setHeight_cm(request.getHeight_cm());
            bird.setPlace_of_found(request.getPlace_of_found());
            bird.setWeight_kg(request.getWeight_kg());
            bird.setSpecies(request.getSpecies());

            birdRepository.save(bird);

            return ServiceResponse.ok();
        } catch (Exception e) {
            log.error("Error during save", e);
            return ServiceResponse.error("Error during dog save!");
        }
    }

    public ServiceResponse<?> deleteBird(BirdDeleteRequest request) {
        if (request == null) {
            return ServiceResponse.error("Please provide a valid delete request!");
        }

        if (request.getId() == null || request.getId().isBlank()) {
            return ServiceResponse.error("Id value should be provided!");
        }

        Optional<Bird> birdOptional = birdRepository.findById(request.getId());
        if (birdOptional.isPresent()) {
            birdRepository.deleteById(birdOptional.get().getId());
            return ServiceResponse.ok();
        } else {
            return ServiceResponse.error("Bird not present in database");
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

    public List<Bird> searchBirdByName(String name) {
        return (List<Bird>) birdRepository.findByName(name.toLowerCase());
    }

    public List<Bird> getBirdLimit(int limit) {
        return birdRepository.findAll(PageRequest.of(0, limit)).getContent();
    }
}
