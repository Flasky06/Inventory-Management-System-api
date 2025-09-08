package com.tritva.Inventory_Management.service.impl;

import com.tritva.Inventory_Management.mapper.CategoryMapper;
import com.tritva.Inventory_Management.model.dto.CategoryDto;
import com.tritva.Inventory_Management.model.entity.Category;
import com.tritva.Inventory_Management.repository.CategoryRepository;
import com.tritva.Inventory_Management.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(savedCategory);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findCategoryById(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new EntityNotFoundException("Category not found with that ID"));
        return categoryMapper.toDto(category);
    }


    @Override
    public CategoryDto updateCategory(UUID categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new EntityNotFoundException("Category not found with that ID"));

        category.setName(categoryDto.getName());

        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(updatedCategory);
    }

    @Override
    public void deleteCategory(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new EntityNotFoundException("Category not found with that ID"));

        categoryRepository.delete(category);
    }
}
