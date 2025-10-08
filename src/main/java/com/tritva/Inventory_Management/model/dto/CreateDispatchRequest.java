package com.tritva.Inventory_Management.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDispatchRequest {
    private UUID sourceShopId;
    private UUID destinationShopId;
    private List<DispatchItemRequest> items;
}
