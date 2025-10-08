package com.tritva.Inventory_Management.repository;

import com.tritva.Inventory_Management.model.entity.DispatchBatch;
import com.tritva.Inventory_Management.model.entity.DispatchItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DispatchItemRepository extends JpaRepository<DispatchItem, UUID> {
}
