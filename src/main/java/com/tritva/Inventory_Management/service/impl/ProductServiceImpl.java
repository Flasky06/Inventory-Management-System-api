package com.tritva.Inventory_Management.service.impl;

import com.tritva.Inventory_Management.mapper.ProductMapper;
import com.tritva.Inventory_Management.model.dto.ProductDto;
import com.tritva.Inventory_Management.model.entity.Category;
import com.tritva.Inventory_Management.model.entity.Product;
import com.tritva.Inventory_Management.repository.ProductRepository;
import com.tritva.Inventory_Management.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Override
    public List<ProductDto> listProducts() {
        return List.of();
    }

    @Override
    public ProductDto getProductById(UUID productId, ProductDto productDto) {
        return null;
    }

    @Override
    public ProductDto updateProduct(UUID productId) {
        return null;
    }

    @Override
    public void deleteProduct(UUID productId) {

    }
}
