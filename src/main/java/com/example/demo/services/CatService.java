package com.example.demo.services;

import com.example.demo.dto.*;
import com.example.demo.model.Cat;
import com.example.demo.model.Dog;
import com.example.demo.repositories.CatRepository;
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
public class CatService {

    private final CatRepository catRepository;

    @Value("${cats.images_path}")
    private String imagesPath;

    @Value("${cats.images_url}")
    private String imagesUrl;

    public List<Cat> getCat() {
        return (List<Cat>) catRepository.findAll();
    }

    public ServiceResponse<?> addCat(CatWriteRequest request) {
        log.debug("Received addCat request: {}", request);

        if (request == null) {
            return ServiceResponse.error("Please provide a valid create product request!");
        }

        if (request.getName() == null || request.getName().isBlank()) {
            log.debug("Name is blank");
            return ServiceResponse.error("Name cannot be blank!");
        }


        Cat cat = request.getId() != null ?
                catRepository.findById(request.getId()).orElse(new Cat()) :
                new Cat();

        try {
            if (request.getImage() != null && !request.getImage().isEmpty()) {
                saveImage(request.getImage());
                cat.setImageName(request.getImage().getOriginalFilename());
                cat.setImagePath(imagesUrl + "/" + request.getImage().getOriginalFilename());
            }

            cat.setName(request.getName());
            cat.setDescription(request.getDescription());
            cat.setColors(request.getColors());
            cat.setTemperament(request.getTemperament());
            cat.setOrigin(request.getOrigin());

            catRepository.save(cat);

            return ServiceResponse.ok();
        } catch (Exception e) {
            log.error("Error during save", e);
            return ServiceResponse.error("Error during cats save!");
        }
    }

    public ServiceResponse<?> deleteCat(CatDeleteRequest request) {
        if (request == null) {
            return ServiceResponse.error("Please provide a valid delete request!");
        }

        if (request.getId() == null || request.getId().isBlank()) {
            return ServiceResponse.error("Id value should be provided!");
        }

        Optional<Cat> catOptional = catRepository.findById(request.getId());
        if (catOptional.isPresent()) {
            catRepository.deleteById(catOptional.get().getId());
            return ServiceResponse.ok();
        } else {
            return ServiceResponse.error("Cat not present in database");
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

    public List<Cat> searchCatByName(String name) {
        return (List<Cat>) catRepository.findByName(name.toLowerCase());
    }
    public List<Cat> getCatLimit(int limit) {
        return catRepository.findAll(PageRequest.of(0, limit)).getContent();
    }
}
