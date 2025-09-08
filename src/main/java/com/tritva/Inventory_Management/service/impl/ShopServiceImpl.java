package com.tritva.Inventory_Management.service.impl;

import com.tritva.Inventory_Management.mapper.ShopMapper;
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

    @Override
    public ShopDto createShop(ShopDto shopDto) {
        Shop shop = shopMapper.toEntity(shopDto);
        Shop savedShop = shopRepository.save(shop);
        return shopMapper.toDto(savedShop);
    }

    @Override
    public List<ShopDto> listShops() {
        return shopRepository.findAll()
                .stream()
                .map(shopMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ShopDto getShopById(UUID shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(()->new EntityNotFoundException("Shop not found with that ID"));

        return shopMapper.toDto(shop);
    }

    @Override
    public ShopDto updateShop(UUID shopId, ShopDto shopDto) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(()->new EntityNotFoundException("Shop not found with that ID"));

        shop.setName(shopDto.getName());
        shop.setLocation(shopDto.getLocation());

        Shop updatedShop = shopRepository.save(shop);

        return shopMapper.toDto(updatedShop);
    }

    @Override
    public void deleteShop(UUID shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(()->new EntityNotFoundException("Shop not found with that ID"));

        shopRepository.delete(shop);
    }
}
