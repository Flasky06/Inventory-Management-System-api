package com.tritva.Inventory_Management.service.impl;

import com.tritva.Inventory_Management.mapper.EmployeeMapper;
import com.tritva.Inventory_Management.model.dto.EmployeeDto;
import com.tritva.Inventory_Management.model.entity.Employee;
import com.tritva.Inventory_Management.model.entity.Shop;
import com.tritva.Inventory_Management.repository.EmployeeRepository;
import com.tritva.Inventory_Management.repository.ShopRepository;
import com.tritva.Inventory_Management.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final ShopRepository shopRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);

        if (employeeDto.getShopId() != null) {
            Shop shop = shopRepository.findById(employeeDto.getShopId())
                    .orElseThrow(() -> new EntityNotFoundException("Shop not found with that ID"));
            employee.setShop(shop);
        }

        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDto(savedEmployee);
    }

    @Override
    public List<EmployeeDto> listEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployeeById(UUID employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()->new EntityNotFoundException("Employee not found with that ID"));
        return employeeMapper.toDto(employee);
    }

    @Override
    public EmployeeDto updateEmployee(UUID employeeId, EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with that ID"));

        employee.setFullName(employeeDto.getFullName());
        employee.setRole(employeeDto.getRole());
        employee.setIdNo(employeeDto.getIdNo());
        employee.setPhoneNo(employeeDto.getPhoneNo());
        employee.setDob(employeeDto.getDob());
        employee.setGender(employeeDto.getGender());

        if (employeeDto.getShopId() != null) {
            Shop shop = shopRepository.findById(employeeDto.getShopId())
                    .orElseThrow(() -> new EntityNotFoundException("Shop not found with that ID"));
            employee.setShop(shop);
        }

        Employee updatedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDto(updatedEmployee);
    }

    @Override
    public void deleteEmployee(UUID employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with that ID"));

        employeeRepository.delete(employee);
    }

}
