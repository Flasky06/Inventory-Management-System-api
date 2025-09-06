package com.tritva.Inventory_Management.config;

import com.tritva.Inventory_Management.model.Role;
import com.tritva.Inventory_Management.model.entity.User;
import com.tritva.Inventory_Management.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
@AllArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        seedAdminUser();
    }

    private void seedAdminUser() {
        if (!userRepository.existsByUsername("admin")) {
            User adminUser = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .createdAt(LocalDateTime.now())
                    .build();

            userRepository.save(adminUser);
            System.out.println("Admin user created successfully!");
            System.out.println("Username: admin");
            System.out.println("Password: admin123");
        }

        // Create other test users
        if (!userRepository.existsByUsername("ceo")) {
            User ceoUser = User.builder()
                    .username("ceo")
                    .password(passwordEncoder.encode("ceo123"))
                    .role(Role.CEO)
                    .createdAt(LocalDateTime.now())
                    .build();

            userRepository.save(ceoUser);
            System.out.println("CEO user created successfully!");
        }

        if (!userRepository.existsByUsername("workshop_manager")) {
            User workshopManager = User.builder()
                    .username("workshop_manager")
                    .password(passwordEncoder.encode("workshop123"))
                    .role(Role.WORKSHOP_MANAGER)
                    .createdAt(LocalDateTime.now())
                    .build();

            userRepository.save(workshopManager);
            System.out.println("Workshop Manager user created successfully!");
        }
    }
}
