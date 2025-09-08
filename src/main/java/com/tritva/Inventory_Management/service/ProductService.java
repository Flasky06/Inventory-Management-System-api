package com.tritva.Inventory_Management.service;

import com.tritva.Inventory_Management.model.dto.ProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);

    List<ProductDto> listProducts();

    ProductDto getProductById(UUID productId, ProductDto productDto);

    ProductDto updateProduct(UUID productId);

    void deleteProduct(UUID productId);
}
