package com.tritva.Inventory_Management.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispatchItemResponse {
    private UUID id;
    private UUID productId;
    private String productName;
    private Integer quantity;
}
