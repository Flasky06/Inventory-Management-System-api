package com.tritva.Inventory_Management.mapper;

import com.tritva.Inventory_Management.model.dto.DispatchRecordDto;
import com.tritva.Inventory_Management.model.entity.DispatchRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DispatchRecordMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "shop.id", target = "shopId")
    @Mapping(source = "dispatchedBy.id", target = "dispatchedByUserId")
    @Mapping(source = "receivedBy.id", target = "receivedByUserId")
    DispatchRecordDto toDto(DispatchRecord dispatchRecord);

    @Mapping(source = "productId", target = "product.id")
    @Mapping(source = "shopId", target = "shop.id")
    @Mapping(source = "dispatchedByUserId", target = "dispatchedBy.id")
    @Mapping(source = "receivedByUserId", target = "receivedBy.id")
    DispatchRecord toEntity(DispatchRecordDto dispatchRecordDto);
}