package com.tritva.Inventory_Management.service;

import com.tritva.Inventory_Management.model.dto.ProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);

    List<ProductDto> getAllProducts();

    ProductDto getProductById(UUID productId);

    ProductDto updateProduct(UUID productId, ProductDto productDto);

    void deleteProduct(UUID productId);

    List<ProductDto> searchProducts(String productName, UUID categoryId);
}
