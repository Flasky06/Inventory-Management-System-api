package com.tritva.Inventory_Management.controller;

import com.tritva.Inventory_Management.model.dto.CategoryDto;
import com.tritva.Inventory_Management.model.dto.EmployeeDto;
import com.tritva.Inventory_Management.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasAnyRole('ADMIN', 'CEO','WORKSHOP_MANAGER')")
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(categoryService.createCategory(categoryDto));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new CategoryDto(null, e.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CEO','WORKSHOP_MANAGER')")
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> category = categoryService.getAllCategories();
        return ResponseEntity.ok(category);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CEO','WORKSHOP_MANAGER')")
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable UUID categoryId) {
        CategoryDto category = categoryService.findCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CEO','WORKSHOP_MANAGER')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable UUID categoryId,
                                                      @RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategory = categoryService.updateCategory(categoryId, categoryDto);
        return ResponseEntity.ok(updatedCategory);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CEO','WORKSHOP_MANAGER')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteShop(@PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
