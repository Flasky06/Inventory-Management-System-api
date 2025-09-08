package com.tritva.Inventory_Management.repository;

import com.tritva.Inventory_Management.model.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShopRepository extends JpaRepository<Shop, UUID> {
}
