package com.tritva.Inventory_Management.service;

import com.tritva.Inventory_Management.model.ShopType;
import com.tritva.Inventory_Management.model.dto.ShopDto;

import java.util.List;
import java.util.UUID;

public interface ShopService {
    ShopDto createShop(ShopDto shopDto);

    List<ShopDto> getAllShops();

    ShopDto getShopById(UUID shopId);

    ShopDto updateShop(UUID shopId, ShopDto shopDto);

    void deleteShop(UUID shopId);

    List<ShopDto> getShopsByType(ShopType shopType);

}
