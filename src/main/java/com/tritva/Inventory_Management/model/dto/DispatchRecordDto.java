package com.tritva.Inventory_Management.model.dto;

import com.tritva.Inventory_Management.model.DispatchStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispatchRecordDto {

    private UUID id;
    private UUID productId;
    private UUID shopId;
    private Integer quantity;
    private DispatchStatus status;
    private UUID dispatchedByUserId;
    private UUID receivedByUserId;
    private LocalDateTime dispatchedAt;
    private LocalDateTime lastUpdatedAt;
}