package com.tritva.Inventory_Management.repository;

import com.tritva.Inventory_Management.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
