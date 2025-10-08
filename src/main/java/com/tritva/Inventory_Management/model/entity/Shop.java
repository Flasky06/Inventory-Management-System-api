package com.tritva.Inventory_Management.model.entity;

import com.tritva.Inventory_Management.model.Role;
import com.tritva.Inventory_Management.model.ShopType;
import jakarta.persistence.*;
import lombok.Getter; // Import the Getter annotation
import lombok.Setter; // Import the Setter annotation
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter // Add this
@Setter // Add this
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "shops")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShopType shopType;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Employee> employees;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<ShopInventory> inventory;

    @Column(nullable = false, name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}