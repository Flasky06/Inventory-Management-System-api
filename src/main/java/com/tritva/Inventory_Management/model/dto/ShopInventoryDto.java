package com.tritva.Inventory_Management.model.dto;

import com.tritva.Inventory_Management.model.InventoryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopInventoryDto {

    private UUID id;
    private UUID shopId;
    private UUID productId;
    private Integer quantity;
    private Integer pendingQuantity;
    private InventoryStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}