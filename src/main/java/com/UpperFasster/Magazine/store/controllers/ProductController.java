package com.UpperFasster.Magazine.store.controllers;

import com.UpperFasster.Magazine.store.DTO.NewProductDTO;
import com.UpperFasster.Magazine.store.DTO.UpdateProductDTO;
import com.UpperFasster.Magazine.store.models.Product;
import com.UpperFasster.Magazine.store.services.ProductService;
import com.UpperFasster.Magazine.store.services.RecommendationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
public class ProductController {

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private ProductService productService;

    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping(value = "/addProduct")
    public void AddProduct(
            @RequestPart(name = "data") String productDTO,
            @RequestPart(name = "main-img") MultipartFile img,
            @RequestPart(name = "additional-images", required = false) List<MultipartFile> images) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        NewProductDTO newProductDTO = objectMapper.readValue(productDTO, NewProductDTO.class);

        productService.addNewProduct(newProductDTO, img, images);
    }

    @PutMapping("/updateProduct")
    public void updateProduct(
            @RequestPart UpdateProductDTO product,
            @RequestParam(required = false) MultipartFile img,
            @RequestParam(required = false) List<MultipartFile> images
    ) throws IOException {
        productService.updateProduct(product, img, images);
    }

    @DeleteMapping("/delProduct/{product_id}")
    public void DelProduct(@PathVariable UUID product_id) {
        productService.deleteProduct(product_id);
    }

    @GetMapping("/getRec/{id}")
    public List<Product> getRecommendations(@PathVariable UUID id, HttpServletRequest request) {
        return recommendationService.getRecommendedProductsForUser(id);
    }

    @GetMapping("/likeItem/{product_id}/{user_id}")
    public void likeProduct(@PathVariable UUID product_id, @PathVariable UUID user_id) {
        productService.addLikedUserToProduct(product_id, user_id);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable UUID id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok()
                    .body(product.get());
        }
        return ResponseEntity.badRequest().body("Object not found");
    }

    @GetMapping("/getPhoto/{fileName}")
    public ResponseEntity<Resource> getPhoto(@PathVariable String fileName) throws IOException {
        Resource image = productService.getPhotoResource(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }




}
