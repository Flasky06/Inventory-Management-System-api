package com.tritva.Inventory_Management.controller;

import com.tritva.Inventory_Management.model.dto.ShopDto;
import com.tritva.Inventory_Management.service.ShopService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
     * @param shopDto The DTO containing the shop details to be created.
     * @return A ResponseEntity containing the created ShopDto and HTTP status.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO')")
    @PostMapping
    public ResponseEntity<ShopDto> createShop(@RequestBody ShopDto shopDto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(shopService.createShop(shopDto));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ShopDto(null,null, e.getMessage()));
        }
    }

    /**
     * Retrieves a list of all shops.
     * Accessible only by ADMIN and CEO roles.
     * @return A ResponseEntity containing a list of all ShopDto objects.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO')")
    @GetMapping
    public ResponseEntity<List<ShopDto>> getAllShops() {
        List<ShopDto> shops = shopService.listShops();
        return ResponseEntity.ok(shops);
    }

    /**
     * Retrieves a single shop by its ID.
     * @param shopId The UUID of the shop to retrieve.
     * @return A ResponseEntity containing the requested ShopDto.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO')")
    @GetMapping("/{shopId}")
    public ResponseEntity<ShopDto> getShopById(@PathVariable UUID shopId) {
        ShopDto shop = shopService.getShopById(shopId);
        return ResponseEntity.ok(shop);
    }

    /**
     * Updates an existing shop.
     * @param shopId The UUID of the shop to update.
     * @param shopDto The DTO containing the updated shop details.
     * @return A ResponseEntity containing the updated ShopDto.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO')")
    @PutMapping("/{shopId}")
    public ResponseEntity<ShopDto> updateShop(@PathVariable UUID shopId, @RequestBody ShopDto shopDto) {
        ShopDto updatedShop = shopService.updateShop(shopId, shopDto);
        return ResponseEntity.ok(updatedShop);
    }

    /**
     * Deletes a shop by its ID.
     * @param shopId The UUID of the shop to delete.
     * @return A ResponseEntity with no content.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO')")
    @DeleteMapping("/{shopId}")
    public ResponseEntity<Void> deleteShop(@PathVariable UUID shopId) {
        shopService.deleteShop(shopId);
        return ResponseEntity.noContent().build();
    }
}
