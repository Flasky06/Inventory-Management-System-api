package com.tritva.Inventory_Management.mapper;

import com.tritva.Inventory_Management.model.dto.ShopDto;
import com.tritva.Inventory_Management.model.entity.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ShopMapper {

    ShopDto toDto(Shop shop);

    Shop toEntity(ShopDto shopDto);
}
