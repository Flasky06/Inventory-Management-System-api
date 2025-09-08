package com.tritva.Inventory_Management.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopDto {
    private UUID id;
    private String name;
    private String location;
}
