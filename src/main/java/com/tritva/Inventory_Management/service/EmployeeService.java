package com.tritva.Inventory_Management.service;

import com.tritva.Inventory_Management.model.dto.EmployeeDto;
import com.tritva.Inventory_Management.model.dto.ShopDto;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);

    List<EmployeeDto> listEmployees();

    EmployeeDto getEmployeeById(UUID employeeId);

    EmployeeDto updateEmployee(UUID employeeId, EmployeeDto employeeDto);

    void deleteEmployee(UUID employeeId);
}
