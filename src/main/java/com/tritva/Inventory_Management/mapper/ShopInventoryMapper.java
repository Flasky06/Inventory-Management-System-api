package com.tritva.Inventory_Management.mapper;

import com.tritva.Inventory_Management.model.dto.ShopInventoryDto;
import com.tritva.Inventory_Management.model.entity.ShopInventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ShopInventoryMapper {

    @Mapping(source = "shop.id", target = "shopId")
    @Mapping(source = "product.id", target = "productId")
    ShopInventoryDto toDto(ShopInventory shopInventory);

    @Mapping(source = "shopId", target = "shop.id")
    @Mapping(source = "productId", target = "product.id")
    ShopInventory toEntity(ShopInventoryDto shopInventoryDto);
}