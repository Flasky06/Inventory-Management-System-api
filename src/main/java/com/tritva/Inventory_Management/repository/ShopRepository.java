package com.tritva.Inventory_Management.repository;

import com.tritva.Inventory_Management.model.ShopType;
import com.tritva.Inventory_Management.model.entity.Shop;
import com.tritva.Inventory_Management.model.entity.ShopInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ShopRepository extends JpaRepository<Shop, UUID> {
    List<Shop> findByShopType(ShopType shopType);
}
