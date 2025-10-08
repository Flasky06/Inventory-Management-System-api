package com.tritva.Inventory_Management.service.impl;

import com.tritva.Inventory_Management.mapper.ShopMapper;
import com.tritva.Inventory_Management.model.ShopType;
import com.tritva.Inventory_Management.model.dto.ShopDto;
import com.tritva.Inventory_Management.model.entity.Shop;
import com.tritva.Inventory_Management.repository.ShopRepository;
import com.tritva.Inventory_Management.service.ShopService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;


    /**
     * Creates a new shop and saves it to the database.
     *
     * @param shopDto The DTO containing the shop details.
     * @return The created ShopDto.
     */
    @Override
    public ShopDto createShop(ShopDto shopDto) {
        Shop shop = shopMapper.toEntity(shopDto);
        Shop savedShop = shopRepository.save(shop);
        return shopMapper.toDto(savedShop);
    }

    /**
     * Retrieves a list of all shops from the database.
     *
     * @return A list of all ShopDto objects.
     */
    @Override
    public List<ShopDto> getAllShops() {
        return shopRepository.findAll()
                .stream()
                .map(shopMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single shop by its ID.
     *
     * @param shopId The UUID of the shop to retrieve.
     * @return The requested ShopDto.
     * @throws EntityNotFoundException if the shop is not found.
     */
    @Override
    public ShopDto getShopById(UUID shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new EntityNotFoundException("Shop not found with ID: " + shopId));
        return shopMapper.toDto(shop);
    }

    /**
     * Updates an existing shop.
     *
     * @param shopId  The UUID of the shop to update.
     * @param shopDto The DTO with the updated shop details.
     * @return The updated ShopDto.
     * @throws EntityNotFoundException if the shop to update is not found.
     */
    @Override
    public ShopDto updateShop(UUID shopId, ShopDto shopDto) {
        Shop existingShop = shopRepository.findById(shopId)
                .orElseThrow(() -> new EntityNotFoundException("Shop not found with ID: " + shopId));


        existingShop.setName(shopDto.getName());
        existingShop.setLocation(shopDto.getLocation());
        existingShop.setShopType(shopDto.getShopType());
        Shop updatedShop = shopRepository.save(existingShop);
        return shopMapper.toDto(updatedShop);
    }

    /**
     * Deletes a shop by its ID.
     *
     * @param shopId The UUID of the shop to delete.
     * @throws EntityNotFoundException if the shop to delete is not found.
     */
    @Override
    public void deleteShop(UUID shopId) {
        if (!shopRepository.existsById(shopId)) {
            throw new EntityNotFoundException("Shop not found with ID: " + shopId);
        }
        shopRepository.deleteById(shopId);
    }

    /**
     * Retrieves a list of shops by their type (e.g., RETAIL or WORKSHOP).
     *
     * @param shopType The type of shops to retrieve.
     * @return A list of ShopDto objects matching the provided type.
     */
    @Override
    public List<ShopDto> getShopsByType(ShopType shopType) {
        return shopRepository.findByShopType(shopType)
                .stream()
                .map(shopMapper::toDto)
                .collect(Collectors.toList());
    }
}
