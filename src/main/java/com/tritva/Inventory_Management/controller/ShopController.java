package com.tritva.Inventory_Management.controller;

import com.tritva.Inventory_Management.model.ShopType;
import com.tritva.Inventory_Management.model.dto.ShopDto;
import com.tritva.Inventory_Management.service.ShopService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/shop")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ShopController {

    private final ShopService shopService;

    /**
     * Creates a new shop.
     * Accessible only by ADMIN and CEO roles.
     * @param shopDto The DTO containing the shop details to be created.
     * @return A ResponseEntity containing the created ShopDto and HTTP status.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO')")
    @PostMapping
    public ResponseEntity<ShopDto> createShop(@RequestBody ShopDto shopDto) {
        ShopDto createdShop = shopService.createShop(shopDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShop);
    }

    /**
     * Retrieves a list of all shops.
     * Accessible only by ADMIN and CEO roles.
     * @return A ResponseEntity containing a list of all ShopDto objects.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'SHOP_MANAGER', 'WORKSHOP_MANAGER')")
    @GetMapping
    public ResponseEntity<List<ShopDto>> getAllShops() {
        List<ShopDto> shops = shopService.getAllShops();
        return ResponseEntity.ok(shops);
    }

    /**
     * Retrieves a single shop by its ID.
     * Accessible only by ADMIN and CEO roles.
     * @param shopId The UUID of the shop to retrieve.
     * @return A ResponseEntity containing the requested ShopDto.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'SHOP_MANAGER', 'WORKSHOP_MANAGER')")
    @GetMapping("/{shopId}")
    public ResponseEntity<ShopDto> getShopById(@PathVariable UUID shopId) {
        try {
            ShopDto shop = shopService.getShopById(shopId);
            return ResponseEntity.ok(shop);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Updates an existing shop.
     * Accessible only by ADMIN and CEO roles.
     * @param shopId The UUID of the shop to update.
     * @param shopDto The DTO containing the updated shop details.
     * @return A ResponseEntity containing the updated ShopDto.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'SHOP_MANAGER', 'WORKSHOP_MANAGER')")
    @PutMapping("/{shopId}")
    public ResponseEntity<ShopDto> updateShop(@PathVariable UUID shopId, @RequestBody ShopDto shopDto) {
        try {
            ShopDto updatedShop = shopService.updateShop(shopId, shopDto);
            return ResponseEntity.ok(updatedShop);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Deletes a shop by its ID.
     * Accessible only by ADMIN and CEO roles.
     * @param shopId The UUID of the shop to delete.
     * @return A ResponseEntity with no content.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO')")
    @DeleteMapping("/{shopId}")
    public ResponseEntity<Void> deleteShop(@PathVariable UUID shopId) {
        try {
            shopService.deleteShop(shopId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Retrieves a list of shops by their type.
     * Accessible only by ADMIN and CEO roles.
     * @param shopType The type of shops to retrieve (e.g., RETAIL or WORKSHOP).
     * @return A ResponseEntity containing a list of ShopDto objects matching the provided type.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'SHOP_MANAGER', 'WORKSHOP_MANAGER')")
    @GetMapping("/type")
    public ResponseEntity<List<ShopDto>> getShopsByType(@RequestParam("shopType") ShopType shopType) {
        List<ShopDto> shops = shopService.getShopsByType(shopType);
        return ResponseEntity.ok(shops);
    }
}
