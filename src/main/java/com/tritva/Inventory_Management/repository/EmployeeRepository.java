package com.tritva.Inventory_Management.repository;

import com.tritva.Inventory_Management.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
}
