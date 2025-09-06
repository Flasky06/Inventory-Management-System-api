package com.tritva.Inventory_Management.mapper;

import com.tritva.Inventory_Management.model.dto.EmployeeDto;
import com.tritva.Inventory_Management.model.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {

    @Mapping(source = "shop.id", target = "shopId")
    @Mapping(source = "shop.name", target = "shopName")
    EmployeeDto toDto(Employee employee);

    @Mapping(source = "shopId", target = "shop.id")
    Employee toEntity(EmployeeDto employeeDto);
}