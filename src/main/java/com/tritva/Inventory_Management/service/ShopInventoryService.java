package com.tritva.Inventory_Management.service;

import com.tritva.Inventory_Management.model.dto.ShopInventoryDto;

import java.util.List;
import java.util.UUID;

public interface ShopInventoryService {
    ShopInventoryDto createInventory(ShopInventoryDto inventoryDto);
    List<ShopInventoryDto> getAllInventory();
    List<ShopInventoryDto> getInventoryByShop(UUID shopId);
    ShopInventoryDto getInventoryByShopAndProduct(UUID shopId, UUID productId);
    ShopInventoryDto getInventoryById(UUID id);
    ShopInventoryDto updateInventory(UUID id, ShopInventoryDto inventoryDto);
    ShopInventoryDto setQuantity(UUID shopId, UUID productId, int quantity);
    ShopInventoryDto addInventory(UUID shopId, UUID productId, int quantity);
    ShopInventoryDto reduceInventory(UUID shopId, UUID productId, int quantity);
    void deleteInventory(UUID id);

}
