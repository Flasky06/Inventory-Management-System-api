package com.tritva.Inventory_Management.service;

import com.tritva.Inventory_Management.model.dto.*;

import java.util.List;
import java.util.UUID;

public interface AuthService {
    AuthResponseDto login(LoginDto loginDto);

    void register(RegisterDto registerDto);

    List<UserDto> getAllUsers();

    void updateUser(UUID userId, UserUpdateDto userUpdateDto);

    void deleteUser(UUID userId);
}
