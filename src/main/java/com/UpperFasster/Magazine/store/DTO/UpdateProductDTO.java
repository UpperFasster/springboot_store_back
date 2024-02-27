package com.UpperFasster.Magazine.store.DTO;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductDTO {
    private UUID id;
    private String name;
    private int price;
    private int discount;
    private String currency;
    @Min(0)
    @Positive
    private int quantity;
    @Size(message = "Min length that was 10", min = 10, max = 255)
    private String description;
    @Nullable
    private UUID photoId;
    private List<UUID> imagesToDelete;
}
