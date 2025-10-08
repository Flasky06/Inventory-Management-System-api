package com.tritva.Inventory_Management.service.impl;

import com.tritva.Inventory_Management.mapper.ShopInventoryMapper;
import com.tritva.Inventory_Management.model.InventoryStatus;
import com.tritva.Inventory_Management.model.dto.ShopInventoryDto;
import com.tritva.Inventory_Management.model.entity.Product;
import com.tritva.Inventory_Management.model.entity.Shop;
import com.tritva.Inventory_Management.model.entity.ShopInventory;
import com.tritva.Inventory_Management.repository.ProductRepository;
import com.tritva.Inventory_Management.repository.ShopInventoryRepository;
import com.tritva.Inventory_Management.repository.ShopRepository;
import com.tritva.Inventory_Management.service.ShopInventoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class ShopInventoryServiceImpl implements ShopInventoryService {

    private final ShopInventoryRepository shopInventoryRepository;
    private final ShopInventoryMapper shopInventoryMapper;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;

    @Override
    public ShopInventoryDto createInventory(ShopInventoryDto inventoryDto) {
        Shop shop = shopRepository.findById(inventoryDto.getShopId())
                .orElseThrow(() -> new RuntimeException("Shop not found"));

        Product product = productRepository.findById(inventoryDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        shopInventoryRepository.findByShopIdAndProductId(shop.getId(), product.getId())
                .ifPresent(existing -> {
                    throw new RuntimeException("Inventory already exists for this shop and product");
                });

        ShopInventory inventory = ShopInventory.builder()
                .shop(shop)
                .product(product)
                .quantity(inventoryDto.getQuantity())
                .inventoryStatus(inventoryDto.getQuantity() > 0 ?
                        InventoryStatus.AVAILABLE : InventoryStatus.OUT_OF_STOCK)
                .build();

        ShopInventory saved = shopInventoryRepository.save(inventory);
        return shopInventoryMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShopInventoryDto> getAllInventory() {
        List<ShopInventory> inventories = shopInventoryRepository.findAll();
        return shopInventoryMapper.toDtoList(inventories);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShopInventoryDto> getInventoryByShop(UUID shopId) {
        if (!shopRepository.existsById(shopId)) {
            throw new RuntimeException("Shop not found");
        }
        List<ShopInventory> inventories = shopInventoryRepository.findByShopId(shopId);
        return shopInventoryMapper.toDtoList(inventories);
    }

    @Override
    @Transactional(readOnly = true)
    public ShopInventoryDto getInventoryByShopAndProduct(UUID shopId, UUID productId) {
        ShopInventory inventory = shopInventoryRepository.findByShopIdAndProductId(shopId, productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for this shop and product"));
        return shopInventoryMapper.toDto(inventory);
    }

    @Override
    @Transactional(readOnly = true)
    public ShopInventoryDto getInventoryById(UUID id) {
        ShopInventory inventory = shopInventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        return shopInventoryMapper.toDto(inventory);
    }

    @Override
    public ShopInventoryDto updateInventory(UUID id, ShopInventoryDto inventoryDto) {
        ShopInventory inventory = shopInventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        if (inventoryDto.getQuantity() != null) {
            inventory.setQuantity(inventoryDto.getQuantity());
            inventory.setInventoryStatus(inventoryDto.getQuantity() > 0 ?
                    InventoryStatus.AVAILABLE : InventoryStatus.OUT_OF_STOCK);
        }

        ShopInventory updated = shopInventoryRepository.save(inventory);
        return shopInventoryMapper.toDto(updated);
    }

    @Override
    public ShopInventoryDto setQuantity(UUID shopId, UUID productId, int quantity) {
        if (quantity < 0) {
            throw new RuntimeException("Quantity cannot be negative");
        }

        ShopInventory inventory = shopInventoryRepository.findByShopIdAndProductId(shopId, productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        inventory.setQuantity(quantity);
        inventory.setInventoryStatus(quantity > 0 ? InventoryStatus.AVAILABLE : InventoryStatus.OUT_OF_STOCK);
        ShopInventory updated = shopInventoryRepository.save(inventory);
        return shopInventoryMapper.toDto(updated);
    }

    @Override
    public ShopInventoryDto addInventory(UUID shopId, UUID productId, int quantity) {
        if (quantity <= 0) {
            throw new RuntimeException("Quantity to add must be positive");
        }

        ShopInventory inventory = shopInventoryRepository.findByShopIdAndProductId(shopId, productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        int newQuantity = inventory.getQuantity() + quantity;
        inventory.setQuantity(newQuantity);
        inventory.setInventoryStatus(newQuantity > 0 ? InventoryStatus.AVAILABLE : InventoryStatus.OUT_OF_STOCK);
        ShopInventory updated = shopInventoryRepository.save(inventory);
        return shopInventoryMapper.toDto(updated);
    }

    @Override
    public ShopInventoryDto reduceInventory(UUID shopId, UUID productId, int quantity) {
        if (quantity <= 0) {
            throw new RuntimeException("Quantity to reduce must be positive");
        }

        ShopInventory inventory = shopInventoryRepository.findByShopIdAndProductId(shopId, productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        int newQuantity = inventory.getQuantity() - quantity;
        if (newQuantity < 0) {
            throw new RuntimeException("Insufficient inventory. Available: " + inventory.getQuantity());
        }

        inventory.setQuantity(newQuantity);
        inventory.setInventoryStatus(newQuantity > 0 ? InventoryStatus.AVAILABLE : InventoryStatus.OUT_OF_STOCK);
        ShopInventory updated = shopInventoryRepository.save(inventory);
        return shopInventoryMapper.toDto(updated);
    }

    @Override
    public void deleteInventory(UUID id) {
        if (!shopInventoryRepository.existsById(id)) {
            throw new RuntimeException("Inventory not found");
        }
        shopInventoryRepository.deleteById(id);
    }
}