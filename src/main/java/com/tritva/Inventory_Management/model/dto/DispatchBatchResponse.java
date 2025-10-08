package com.tritva.Inventory_Management.model.dto;

import com.tritva.Inventory_Management.model.DispatchStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispatchBatchResponse {
    private UUID id;
    private UUID sourceShopId;
    private String sourceShopName;
    private UUID destinationShopId;
    private String destinationShopName;
    private List<DispatchItemResponse> items;
    private DispatchStatus status;
    private String dispatchReference;
    private LocalDateTime createdAt;
    private LocalDateTime dispatchedAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime cancelledAt;
}
