package com.tritva.Inventory_Management.service.impl;

import com.tritva.Inventory_Management.mapper.ProductMapper;
import com.tritva.Inventory_Management.model.dto.ProductDto;
import com.tritva.Inventory_Management.model.entity.Category;
import com.tritva.Inventory_Management.model.entity.Product;
import com.tritva.Inventory_Management.repository.CategoryRepository;
import com.tritva.Inventory_Management.repository.ProductRepository;
import com.tritva.Inventory_Management.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        if (productRepository.existsByProductName(productDto.getProductName())) {
            throw new IllegalArgumentException("Product with this name already exists");
        }

        Product product = productMapper.toEntity(productDto);
        if (productDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with that ID"));
            product.setCategory(category);
        }

        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with that ID"));
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto updateProduct(UUID productId, ProductDto productDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with that ID"));

        product.setProductName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setMaterial(productDto.getMaterial());
        product.setColor(productDto.getColor());
        product.setSize(productDto.getSize());
        product.setPrice(productDto.getPrice());

        if (productDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with that ID"));
            product.setCategory(category);
        }

        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Override
    public void deleteProduct(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with that ID"));

        productRepository.delete(product);
    }

    @Override
    public List<ProductDto> searchProducts(String productName, UUID categoryId) {
        List<Product> products;

        boolean hasProductName = StringUtils.hasText(productName);
        boolean hasCategoryId = categoryId != null;

        if (hasProductName && hasCategoryId) {
            products = productRepository
                    .findByProductNameContainingIgnoreCaseAndCategoryId(productName, categoryId);
        } else if (hasProductName) {
            products = productRepository.findByProductNameContainingIgnoreCase(productName);
        } else if (hasCategoryId) {
            products = productRepository.findByCategoryId(categoryId);
        } else {
            products = productRepository.findAll();
        }

        return products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
