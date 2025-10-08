package com.tritva.Inventory_Management.model.dto;

import com.tritva.Inventory_Management.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDto {
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotNull(message = "Role cannot be null")
    private Role role;

    private UUID shopId;
}

