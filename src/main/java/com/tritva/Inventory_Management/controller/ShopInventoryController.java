package com.tritva.Inventory_Management.controller;

import com.tritva.Inventory_Management.model.dto.ShopInventoryDto;
import com.tritva.Inventory_Management.service.ShopInventoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/inventory")
@AllArgsConstructor
public class ShopInventoryController {

    private final ShopInventoryService shopInventoryService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'SHOP_MANAGER', 'WORKSHOP_MANAGER')")
    public ResponseEntity<ShopInventoryDto> createInventory(@RequestBody ShopInventoryDto inventoryDto) {
        ShopInventoryDto created = shopInventoryService.createInventory(inventoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'SHOP_MANAGER', 'WORKSHOP_MANAGER', 'SALES_PERSON')")
    public ResponseEntity<List<ShopInventoryDto>> getAllInventory() {
        return ResponseEntity.ok(shopInventoryService.getAllInventory());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'SHOP_MANAGER', 'WORKSHOP_MANAGER', 'SALES_PERSON')")
    public ResponseEntity<ShopInventoryDto> getInventoryById(@PathVariable UUID id) {
        return ResponseEntity.ok(shopInventoryService.getInventoryById(id));
    }

    @GetMapping("/shop/{shopId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'SHOP_MANAGER', 'WORKSHOP_MANAGER', 'SALES_PERSON')")
    public ResponseEntity<List<ShopInventoryDto>> getInventoryByShop(@PathVariable UUID shopId) {
        return ResponseEntity.ok(shopInventoryService.getInventoryByShop(shopId));
    }

    @GetMapping("/shop/{shopId}/product/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'SHOP_MANAGER', 'WORKSHOP_MANAGER', 'SALES_PERSON')")
    public ResponseEntity<ShopInventoryDto> getInventoryByShopAndProduct(
            @PathVariable UUID shopId,
            @PathVariable UUID productId) {
        return ResponseEntity.ok(shopInventoryService.getInventoryByShopAndProduct(shopId, productId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'SHOP_MANAGER', 'WORKSHOP_MANAGER')")
    public ResponseEntity<ShopInventoryDto> updateInventory(
            @PathVariable UUID id,
            @RequestBody ShopInventoryDto inventoryDto) {
        return ResponseEntity.ok(shopInventoryService.updateInventory(id, inventoryDto));
    }

    @PatchMapping("/shop/{shopId}/product/{productId}/set-quantity")
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'SHOP_MANAGER', 'WORKSHOP_MANAGER')")
    public ResponseEntity<ShopInventoryDto> setQuantity(
            @PathVariable UUID shopId,
            @PathVariable UUID productId,
            @RequestParam int quantity) {
        return ResponseEntity.ok(shopInventoryService.setQuantity(shopId, productId, quantity));
    }

    @PatchMapping("/shop/{shopId}/product/{productId}/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'SHOP_MANAGER', 'WORKSHOP_MANAGER')")
    public ResponseEntity<ShopInventoryDto> addInventory(
            @PathVariable UUID shopId,
            @PathVariable UUID productId,
            @RequestParam int quantity) {
        return ResponseEntity.ok(shopInventoryService.addInventory(shopId, productId, quantity));
    }

    @PatchMapping("/shop/{shopId}/product/{productId}/reduce")
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'SHOP_MANAGER', 'WORKSHOP_MANAGER')")
    public ResponseEntity<ShopInventoryDto> reduceInventory(
            @PathVariable UUID shopId,
            @PathVariable UUID productId,
            @RequestParam int quantity) {
        return ResponseEntity.ok(shopInventoryService.reduceInventory(shopId, productId, quantity));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO')")
    public ResponseEntity<Void> deleteInventory(@PathVariable UUID id) {
        shopInventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }
}