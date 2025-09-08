package com.tritva.Inventory_Management.service;

import com.tritva.Inventory_Management.model.dto.CategoryDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);

    List<CategoryDto> getAllCategories();

    CategoryDto findCategoryById(UUID categoryId);

    CategoryDto updateCategory(UUID categoryId, CategoryDto categoryDto);

    void deleteCategory(UUID categoryId);
}
