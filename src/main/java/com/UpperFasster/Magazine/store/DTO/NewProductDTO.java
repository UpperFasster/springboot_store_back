package com.UpperFasster.Magazine.store.DTO;

import com.UpperFasster.Magazine.store.models.Product;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewProductDTO {
    private String name;
    private int price;
    private int discount;
    private String currency;
    @Min(0)
    @Positive
    private int quantity;
    @Size(message = "Min length that was 10", min = 10, max = 255)
    private String description;
}
