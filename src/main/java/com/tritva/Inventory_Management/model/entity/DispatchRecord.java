package com.tritva.Inventory_Management.model.entity;

import com.tritva.Inventory_Management.model.DispatchStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "dispatch_records")
public class DispatchRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DispatchStatus status;

    @ManyToOne
    @JoinColumn(name = "dispatched_by_user_id", nullable = false)
    private User dispatchedBy;

    @ManyToOne
    @JoinColumn(name = "received_by_user_id")
    private User receivedBy; // Nullable until received

    @Column(nullable = false, updatable = false)
    private LocalDateTime dispatchedAt;

    @Column(nullable = false)
    private LocalDateTime lastUpdatedAt;

    @PrePersist
    protected void onPrePersist() {
        dispatchedAt = LocalDateTime.now();
        lastUpdatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onPreUpdate() {
        lastUpdatedAt = LocalDateTime.now();
    }
}