package com.tritva.Inventory_Management.repository;

import com.tritva.Inventory_Management.model.entity.Product;
import com.tritva.Inventory_Management.model.entity.Shop;
import com.tritva.Inventory_Management.model.entity.ShopInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShopInventoryRepository extends JpaRepository<ShopInventory, UUID> {
    List<ShopInventory> findByShopId(UUID shopId);
    Optional<ShopInventory> findByShopIdAndProductId(UUID shopId, UUID productId);
}
