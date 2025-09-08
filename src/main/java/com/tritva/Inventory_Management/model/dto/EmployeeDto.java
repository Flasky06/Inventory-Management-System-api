package com.tritva.Inventory_Management.model.dto;

import com.tritva.Inventory_Management.model.Gender;
import com.tritva.Inventory_Management.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private UUID id;
    private Role role;
    private String fullName;
    private String idNo;
    private String phoneNo;
    private LocalDate dob;
    private Gender gender;
    private UUID shopId;
    private String shopName;
}