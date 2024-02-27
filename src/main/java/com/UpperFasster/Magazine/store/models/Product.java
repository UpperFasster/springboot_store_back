package com.UpperFasster.Magazine.store.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private double price;
    private double discount;
    @Column(nullable = false, length = 5)
    private String currency;
    @Column(nullable = false, length = 40)
    private String photo;
    @Column(nullable = true)
    @Size(max = 5)
    private Set<String> additionalImages;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<UUID> likedByUsers;
    @Column(nullable = false)
    private int quantity;
    @Column(length = 255, nullable = false)
    private String description;
}
