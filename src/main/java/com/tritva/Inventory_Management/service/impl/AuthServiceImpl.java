package com.tritva.Inventory_Management.service.impl;

import com.tritva.Inventory_Management.model.dto.*;
import com.tritva.Inventory_Management.model.entity.User;
import com.tritva.Inventory_Management.repository.UserRepository;
import com.tritva.Inventory_Management.security.CustomUserDetails;
import com.tritva.Inventory_Management.security.JwtTokenProvider;
import com.tritva.Inventory_Management.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Override
    public AuthResponseDto login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()
                    )
            );

            String token = jwtTokenProvider.generateToken(authentication);
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            return new AuthResponseDto(
                    token,
                    userDetails.getUsername(),
                    userDetails.getRole().equals("ADMIN") ? com.tritva.Inventory_Management.model.Role.ADMIN :
                            userDetails.getRole().equals("CEO") ? com.tritva.Inventory_Management.model.Role.CEO :
                                    userDetails.getRole().equals("WORKSHOP_MANAGER") ? com.tritva.Inventory_Management.model.Role.WORKSHOP_MANAGER :
                                            userDetails.getRole().equals("SHOP_MANAGER") ? com.tritva.Inventory_Management.model.Role.SHOP_MANAGER :
                                                    com.tritva.Inventory_Management.model.Role.EMPLOYEE,
                    userDetails.getId(),
                    "Login successful"
            );

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public void register(RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new IllegalArgumentException("Username is already taken!");
        }

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(registerDto.getRole());
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateUser(UUID userId, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setUsername(userUpdateDto.getUsername());
        user.setRole(userUpdateDto.getRole());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteById(userId);
    }

    @Override
    public void patchUser(UUID userId, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (userUpdateDto.getUsername() != null) {
            user.setUsername(userUpdateDto.getUsername());
        }
        if (userUpdateDto.getRole() != null) {
            user.setRole(userUpdateDto.getRole());
        }
        userRepository.save(user);
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        return dto;
    }
}
