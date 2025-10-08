package com.tritva.Inventory_Management.model.dto;

import com.tritva.Inventory_Management.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDto {
    private String token;
    private String username;
    private Role role;
    private UUID userId;
    private String message;
    private UUID shopId;

}
