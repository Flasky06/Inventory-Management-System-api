package com.tritva.Inventory_Management.service.impl;
import com.tritva.Inventory_Management.mapper.DispatchMapper;
import com.tritva.Inventory_Management.model.DispatchStatus;
import com.tritva.Inventory_Management.model.InventoryStatus;
import com.tritva.Inventory_Management.model.dto.CreateDispatchRequest;
import com.tritva.Inventory_Management.model.dto.DispatchBatchResponse;
import com.tritva.Inventory_Management.model.dto.DispatchItemRequest;
import com.tritva.Inventory_Management.model.entity.*;
import com.tritva.Inventory_Management.repository.*;
import com.tritva.Inventory_Management.service.DispatchService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class DispatchServiceImpl implements DispatchService {

    private final DispatchBatchRepository dispatchBatchRepository;
    private final DispatchItemRepository dispatchItemRepository;
    private final ShopInventoryRepository shopInventoryRepository;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    private final DispatchMapper dispatchMapper;

    @Override
    public DispatchBatchResponse createDispatch(CreateDispatchRequest request) {
        Shop sourceShop = shopRepository.findById(request.getSourceShopId())
                .orElseThrow(() -> new RuntimeException("Source shop not found"));

        Shop destinationShop = shopRepository.findById(request.getDestinationShopId())
                .orElseThrow(() -> new RuntimeException("Destination shop not found"));

        if (sourceShop.getId().equals(destinationShop.getId())) {
            throw new RuntimeException("Source and destination shops cannot be the same");
        }

        DispatchBatch dispatchBatch = DispatchBatch.builder()
                .sourceShop(sourceShop)
                .destinationShop(destinationShop)
                .status(DispatchStatus.PENDING)
                .dispatchReference(generateDispatchReference())
                .dispatchedAt(LocalDateTime.now())
                .build();

        DispatchBatch savedBatch = dispatchBatchRepository.save(dispatchBatch);

        List<DispatchItem> dispatchItems = new ArrayList<>();
        for (DispatchItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemRequest.getProductId()));

            ShopInventory sourceInventory = shopInventoryRepository
                    .findByShopIdAndProductId(sourceShop.getId(), product.getId())
                    .orElseThrow(() -> new RuntimeException("Product not available in source shop"));

            if (sourceInventory.getQuantity() < itemRequest.getQuantity()) {
                throw new RuntimeException("Insufficient inventory for product: " + product.getProductName());
            }

            int newQuantity = sourceInventory.getQuantity() - itemRequest.getQuantity();
            sourceInventory.setQuantity(newQuantity);
            sourceInventory.setInventoryStatus(newQuantity > 0 ? InventoryStatus.AVAILABLE : InventoryStatus.OUT_OF_STOCK);
            shopInventoryRepository.save(sourceInventory);

            DispatchItem dispatchItem = DispatchItem.builder()
                    .dispatchBatch(savedBatch)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .build();

            dispatchItems.add(dispatchItem);
        }

        dispatchItemRepository.saveAll(dispatchItems);
        savedBatch.setDispatchItems(dispatchItems);

        return dispatchMapper.toResponse(savedBatch);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DispatchBatchResponse> getAllDispatches() {
        return dispatchBatchRepository.findAll().stream()
                .map(dispatchMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DispatchBatchResponse getDispatchById(UUID id) {
        DispatchBatch dispatch = dispatchBatchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispatch not found"));
        return dispatchMapper.toResponse(dispatch);
    }

    @Override
    @Transactional(readOnly = true)
    public DispatchBatchResponse getDispatchByReference(String referenceNumber) {
        DispatchBatch dispatch = dispatchBatchRepository.findByDispatchReference(referenceNumber)
                .orElseThrow(() -> new RuntimeException("Dispatch not found with reference: " + referenceNumber));
        return dispatchMapper.toResponse(dispatch);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DispatchBatchResponse> getDispatchesBySourceShop(UUID shopId) {
        if (!shopRepository.existsById(shopId)) {
            throw new RuntimeException("Shop not found");
        }
        return dispatchBatchRepository.findBySourceShopId(shopId).stream()
                .map(dispatchMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DispatchBatchResponse> getDispatchesByDestinationShop(UUID shopId) {
        if (!shopRepository.existsById(shopId)) {
            throw new RuntimeException("Shop not found");
        }
        return dispatchBatchRepository.findByDestinationShopId(shopId).stream()
                .map(dispatchMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DispatchBatchResponse acknowledgeReceipt(UUID dispatchId, boolean accept) {
        DispatchBatch dispatch = dispatchBatchRepository.findById(dispatchId)
                .orElseThrow(() -> new RuntimeException("Dispatch not found"));

        if (dispatch.getStatus() != DispatchStatus.PENDING) {
            throw new RuntimeException("Can only acknowledge pending dispatches");
        }

        if (accept) {
            for (DispatchItem item : dispatch.getDispatchItems()) {
                ShopInventory destInventory = shopInventoryRepository
                        .findByShopIdAndProductId(dispatch.getDestinationShop().getId(), item.getProduct().getId())
                        .orElse(ShopInventory.builder()
                                .shop(dispatch.getDestinationShop())
                                .product(item.getProduct())
                                .quantity(0)
                                .inventoryStatus(InventoryStatus.OUT_OF_STOCK)
                                .build());

                int newQuantity = destInventory.getQuantity() + item.getQuantity();
                destInventory.setQuantity(newQuantity);
                destInventory.setInventoryStatus(newQuantity > 0 ? InventoryStatus.AVAILABLE : InventoryStatus.OUT_OF_STOCK);
                shopInventoryRepository.save(destInventory);
            }

            dispatch.setStatus(DispatchStatus.RECIEVED);
            dispatch.setDeliveredAt(LocalDateTime.now());
        } else {
            for (DispatchItem item : dispatch.getDispatchItems()) {
                ShopInventory sourceInventory = shopInventoryRepository
                        .findByShopIdAndProductId(dispatch.getSourceShop().getId(), item.getProduct().getId())
                        .orElseThrow(() -> new RuntimeException("Source inventory not found"));

                int newQuantity = sourceInventory.getQuantity() + item.getQuantity();
                sourceInventory.setQuantity(newQuantity);
                sourceInventory.setInventoryStatus(newQuantity > 0 ? InventoryStatus.AVAILABLE : InventoryStatus.OUT_OF_STOCK);
                shopInventoryRepository.save(sourceInventory);
            }

            dispatch.setStatus(DispatchStatus.CANCELLED);
            dispatch.setCancelledAt(LocalDateTime.now());
        }

        DispatchBatch updated = dispatchBatchRepository.save(dispatch);
        return dispatchMapper.toResponse(updated);
    }

    @Override
    public DispatchBatchResponse cancelDispatch(UUID dispatchId) {
        DispatchBatch dispatch = dispatchBatchRepository.findById(dispatchId)
                .orElseThrow(() -> new RuntimeException("Dispatch not found"));

        if (dispatch.getStatus() != DispatchStatus.PENDING) {
            throw new RuntimeException("Can only cancel pending dispatches");
        }

        for (DispatchItem item : dispatch.getDispatchItems()) {
            ShopInventory sourceInventory = shopInventoryRepository
                    .findByShopIdAndProductId(dispatch.getSourceShop().getId(), item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Source inventory not found"));

            int newQuantity = sourceInventory.getQuantity() + item.getQuantity();
            sourceInventory.setQuantity(newQuantity);
            sourceInventory.setInventoryStatus(newQuantity > 0 ? InventoryStatus.AVAILABLE : InventoryStatus.OUT_OF_STOCK);
            shopInventoryRepository.save(sourceInventory);
        }

        dispatch.setStatus(DispatchStatus.CANCELLED);
        dispatch.setCancelledAt(LocalDateTime.now());

        DispatchBatch updated = dispatchBatchRepository.save(dispatch);
        return dispatchMapper.toResponse(updated);
    }

    private String generateDispatchReference() {
        return "DISP-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}