package com.tritva.Inventory_Management.model.entity;

import com.tritva.Inventory_Management.model.DispatchStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "dispatch_batches")
public class DispatchBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_shop_id", nullable = false)
    private Shop sourceShop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_shop_id", nullable = false)
    private Shop destinationShop;

    @OneToMany(mappedBy = "dispatchBatch", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    private List<DispatchItem> dispatchItems;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DispatchStatus status;

    @Column(name = "dispatch_reference", unique = true, nullable = false)
    private String dispatchReference;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime dispatchedAt;

    private LocalDateTime deliveredAt;

    private LocalDateTime cancelledAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = DispatchStatus.PENDING;
        }
    }
}