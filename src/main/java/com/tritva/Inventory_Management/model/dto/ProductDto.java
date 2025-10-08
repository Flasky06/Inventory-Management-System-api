package com.tritva.Inventory_Management.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private UUID id;
    private String productName;
    private String description;
    private String material;
    private String color;
    private String size;
    private BigDecimal price;
    private UUID categoryId;
}