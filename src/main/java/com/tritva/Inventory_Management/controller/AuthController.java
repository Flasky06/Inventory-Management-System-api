package com.tritva.Inventory_Management.controller;

import com.tritva.Inventory_Management.model.dto.*;
import com.tritva.Inventory_Management.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        try {
            AuthResponseDto response = authService.login(loginDto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponseDto(null, null, null, null, "Invalid credentials"));
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CEO')")
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {
        authService.register(registerDto);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CEO')")
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = authService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CEO')")
    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable UUID id,
                                             @Valid @RequestBody UserUpdateDto userUpdateDto) {
        authService.updateUser(id, userUpdateDto);
        return ResponseEntity.ok("User updated successfully");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CEO')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        authService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CEO')")
    @PatchMapping("/users/{userId}")
    public ResponseEntity<String> patchUser(@PathVariable UUID userId, @RequestBody UserUpdateDto userUpdateDto) {
        authService.patchUser(userId, userUpdateDto);
        return new ResponseEntity<>("User patched successfully", HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth endpoint is working!");
    }
}
