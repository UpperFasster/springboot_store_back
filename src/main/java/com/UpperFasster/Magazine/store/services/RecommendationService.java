package com.UpperFasster.Magazine.store.services;

import com.UpperFasster.Magazine.store.models.Product;
import com.UpperFasster.Magazine.store.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getLikedProductsForUser(UUID userId) {
        // Retrieve products that have been liked by the user with the given userId

        return productRepository.findProductsByLikedUsersContains(userId);
    }

    public List<Product> getRecommendedProductsForUser(UUID userId) {
        // Retrieve products liked by the user
        List<Product> userLikedProducts = getLikedProductsForUser(userId);

        // Find users who liked the same products as the current user
        List<UUID> similarUsers = findSimilarUsers(userLikedProducts);

        // Find products liked by similar users but not by the current user

        return findProductsLikedBySimilarUsers(similarUsers, userLikedProducts);
    }

    private List<UUID> findSimilarUsers(List<Product> userLikedProducts) {
        // Logic to find users who liked the same products as the current user
        List<UUID> similarUsers = new ArrayList<>();
        for (Product product : userLikedProducts) {
            similarUsers.addAll(product.getLikedByUsers());
        }
        return similarUsers;
    }

    private List<Product> findProductsLikedBySimilarUsers(List<UUID> similarUsers, List<Product> userLikedProducts) {
        // Logic to find products liked by similar users but not by the current user
        List<Product> recommendedProducts = new ArrayList<>();
        for (UUID user : similarUsers) {
            for (Product product : productRepository.findProductsByLikedUsersContains(user)) {
                if (!userLikedProducts.contains(product) && !recommendedProducts.contains(product)) {
                    recommendedProducts.add(product);
                }
            }
        }
        return recommendedProducts;
    }
}
