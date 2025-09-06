package com.tritva.Inventory_Management.model.dto;

import com.tritva.Inventory_Management.model.Role;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String username;
    private Role role;
}
