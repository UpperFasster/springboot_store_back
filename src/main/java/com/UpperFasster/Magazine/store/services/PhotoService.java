package com.UpperFasster.Magazine.store.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class PhotoService {
    @Value("${spring.photoPath}")
    private String uploadDir;

    public String generateUniqueFileName(MultipartFile photo, UUID new_photo_id) {
        String originalFileName = photo.getOriginalFilename();
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        String newFileNameId = new_photo_id.toString();
        return newFileNameId + "." + fileExtension;
    }

    public String savePhoto(MultipartFile photo, String fileName) throws IOException {
        String filePath = this.uploadDir + fileName;

        photo.transferTo(new File(filePath));
        return filePath;
    }

    public Set<String> saveSetPhoto(List<MultipartFile> images, UUID photo_id) throws IOException {
        Set<String> listOfNamesAdditionalPhotos = new HashSet<>();

        if (images != null && !images.isEmpty()) {
            for (MultipartFile img: images) {
                UUID additionalImageId = UUID.randomUUID();
                String newAdditionalImageId = this.generateUniqueFileName(img, additionalImageId);
                String newAdditionalFileName = String.format("%s_of_%s", photo_id, newAdditionalImageId);
                this.savePhoto(img, newAdditionalFileName);
                listOfNamesAdditionalPhotos.add(newAdditionalFileName);
            }
        }

        return listOfNamesAdditionalPhotos;
    }

    public Resource getPhotoResource(String fileName) throws IOException {
        Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            // Handle resource not found or unreadable
            // You can return a default image or throw an exception
            throw new RuntimeException("Failed to load photo: " + fileName);
        }
    }

    public boolean deletePhoto(String fileName) throws FileNotFoundException {
        File file = new File(uploadDir + fileName);

        if (file.exists()) {
            if (!file.delete()) {
                throw new FileNotFoundException("File " + fileName + " does not exist");
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}
