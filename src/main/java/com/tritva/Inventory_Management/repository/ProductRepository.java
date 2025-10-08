package com.tritva.Inventory_Management.repository;

import com.tritva.Inventory_Management.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    boolean existsByProductName(String productName);
    List<Product> findByCategoryId(UUID categoryId);
    List<Product> findByProductNameContainingIgnoreCase(String productName);
    List<Product> findByProductNameContainingIgnoreCaseAndCategoryId(String productName, UUID categoryId);
}
