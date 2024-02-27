package com.UpperFasster.Magazine.store.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private UUID id;
    private String name;
    private int price;
    private int discount;
    private String currency;
    private String photo;
    private String link;
}
