package com.UpperFasster.Magazine.store.services;

import com.UpperFasster.Magazine.store.DTO.NewProductDTO;
import com.UpperFasster.Magazine.store.DTO.UpdateProductDTO;
import com.UpperFasster.Magazine.store.models.Product;
import com.UpperFasster.Magazine.store.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PhotoService photoService;

    public Optional<Product> getProductById(UUID id_product) {
        return productRepository.findById(id_product);
    }

    public void addLikedUserToProduct(UUID productId, UUID userId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            List<UUID> likedByUsers = product.getLikedByUsers();
            likedByUsers.add(userId);
            product.setLikedByUsers(likedByUsers);

            // Save the updated Product entity
            productRepository.save(product);
        } else {
            // Handle the case where the product ID does not exist
            // e.g., throw an exception, return a specific response, etc.
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public void addNewProduct(NewProductDTO newProductDTO, 
                              MultipartFile mainImage,
                              List<MultipartFile> additionalImages) throws IOException {
        if (additionalImages != null && !additionalImages.isEmpty() && additionalImages.size() >= 5) {
            throw new RuntimeException("Additional images max count is 5");
        }

        UUID newProductId = UUID.randomUUID();
        String newFileName = photoService.generateUniqueFileName(mainImage, newProductId);
        photoService.savePhoto(mainImage, newFileName);
        Set<String> listOfNamesAdditionalPhotos = photoService.saveSetPhoto(additionalImages, newProductId);

        try {
            Product product = Product.builder()
                    .name(newProductDTO.getName())
                    .price(newProductDTO.getPrice())
                    .discount(newProductDTO.getDiscount())
                    .currency(newProductDTO.getCurrency())
                    .photo(newFileName)
                    .additionalImages(listOfNamesAdditionalPhotos)
                    .quantity(newProductDTO.getQuantity())
                    .description(newProductDTO.getDescription())
                    .build();

            productRepository.save(product);
        } catch (DataIntegrityViolationException ignored) {

        }
    }

    public void deleteProduct(UUID product_id) {
        productRepository.deleteById(product_id);
    }

    @Transactional
    public void updateProduct(UpdateProductDTO updateProductDTO,
                              MultipartFile img,
                              List<MultipartFile> images) throws IOException {
        // todo fix that
        Optional<Product> optionalProduct = productRepository.findById(updateProductDTO.getId());
        if (optionalProduct.isEmpty()) {
            throw new EntityNotFoundException("Product not found");
        }

        Product product = optionalProduct.get();

        if (updateProductDTO.getPhotoId() != null && !img.isEmpty()) {
            if (photoService.deletePhoto(updateProductDTO.getPhotoId().toString())) {
                photoService.savePhoto(img, updateProductDTO.getPhotoId().toString());
            } else {
                throw new FileNotFoundException("File not found");
            }
        }

        int currentAdditionalImageCount = product.getAdditionalImages().size();

        int remainingSlots = 5 - currentAdditionalImageCount;

        if (images.size() > remainingSlots) {
            throw new IllegalArgumentException("Cannot add more than 5 images in total.");
        }

        List<UUID> imagesToDelete = updateProductDTO.getImagesToDelete();
        for (UUID imageId : imagesToDelete) {
            if (photoService.deletePhoto(imageId.toString())) {
                product.getAdditionalImages().remove(String.format("%s_of_%s", updateProductDTO.getPhotoId(), imageId.toString()));
            }
        }

        for (MultipartFile image : images) {
            String newImageId = photoService.savePhoto(image, UUID.randomUUID().toString());
            product.getAdditionalImages().add(newImageId);
        }

        product.setName(updateProductDTO.getName());
        product.setPrice(updateProductDTO.getPrice());
        product.setDiscount(updateProductDTO.getDiscount());
        product.setCurrency(updateProductDTO.getCurrency());
        product.setQuantity(updateProductDTO.getQuantity());
        product.setDescription(updateProductDTO.getDescription());

        productRepository.save(product);
    }

    public Resource getPhotoResource(String fileName) throws IOException {
        return photoService.getPhotoResource(fileName);
    }
}
