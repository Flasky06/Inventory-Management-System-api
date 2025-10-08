package com.tritva.Inventory_Management.mapper;

import com.tritva.Inventory_Management.model.dto.DispatchBatchResponse;
import com.tritva.Inventory_Management.model.dto.DispatchItemResponse;
import com.tritva.Inventory_Management.model.entity.DispatchBatch;
import com.tritva.Inventory_Management.model.entity.DispatchItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DispatchMapper {

    @Mapping(target = "sourceShopId", source = "sourceShop.id")
    @Mapping(target = "sourceShopName", source = "sourceShop.name")
    @Mapping(target = "destinationShopId", source = "destinationShop.id")
    @Mapping(target = "destinationShopName", source = "destinationShop.name")
    @Mapping(target = "items", source = "dispatchItems")
    DispatchBatchResponse toResponse(DispatchBatch dispatchBatch);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.productName")
    DispatchItemResponse toItemResponse(DispatchItem dispatchItem);
}
