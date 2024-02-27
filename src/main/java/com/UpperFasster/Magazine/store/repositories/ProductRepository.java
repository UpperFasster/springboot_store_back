package com.UpperFasster.Magazine.store.repositories;

import com.UpperFasster.Magazine.store.controllers.ProductController;
import com.UpperFasster.Magazine.store.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {


    @Query("SELECT p FROM Product p WHERE :userId MEMBER OF p.likedByUsers")
    List<Product> findProductsByLikedUsersContains(@Param("userId") UUID userId);
}
