package com.tritva.Inventory_Management.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispatchItemRequest {
    private UUID productId;
    private int quantity;
}
