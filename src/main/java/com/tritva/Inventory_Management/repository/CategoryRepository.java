package com.tritva.Inventory_Management.repository;

import com.tritva.Inventory_Management.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
