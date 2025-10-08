package com.tritva.Inventory_Management.controller;

import com.tritva.Inventory_Management.model.dto.CreateDispatchRequest;
import com.tritva.Inventory_Management.model.dto.DispatchBatchResponse;
import com.tritva.Inventory_Management.service.DispatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/dispatches")
@AllArgsConstructor
public class DispatchController {

    private final DispatchService dispatchService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'SHOP_MANAGER', 'WORKSHOP_MANAGER')")
    public ResponseEntity<DispatchBatchResponse> createDispatch(@RequestBody CreateDispatchRequest request) {
        DispatchBatchResponse created = dispatchService.createDispatch(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'SHOP_MANAGER', 'WORKSHOP_MANAGER')")
    public ResponseEntity<List<DispatchBatchResponse>> getAllDispatches() {
        return ResponseEntity.ok(dispatchService.getAllDispatches());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'SHOP_MANAGER', 'WORKSHOP_MANAGER')")
    public ResponseEntity<DispatchBatchResponse> getDispatchById(@PathVariable UUID id) {
        return ResponseEntity.ok(dispatchService.getDispatchById(id));
    }

    @GetMapping("/reference/{referenceNumber}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'SHOP_MANAGER', 'WORKSHOP_MANAGER')")
    public ResponseEntity<DispatchBatchResponse> getDispatchByReference(@PathVariable String referenceNumber) {
        return ResponseEntity.ok(dispatchService.getDispatchByReference(referenceNumber));
    }

    @GetMapping("/source-shop/{shopId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'SHOP_MANAGER', 'WORKSHOP_MANAGER')")
    public ResponseEntity<List<DispatchBatchResponse>> getDispatchesBySourceShop(@PathVariable UUID shopId) {
        return ResponseEntity.ok(dispatchService.getDispatchesBySourceShop(shopId));
    }

    @GetMapping("/destination-shop/{shopId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'SHOP_MANAGER', 'WORKSHOP_MANAGER')")
    public ResponseEntity<List<DispatchBatchResponse>> getDispatchesByDestinationShop(@PathVariable UUID shopId) {
        return ResponseEntity.ok(dispatchService.getDispatchesByDestinationShop(shopId));
    }

    @PatchMapping("/{id}/acknowledge")
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'SHOP_MANAGER', 'WORKSHOP_MANAGER')")
    public ResponseEntity<DispatchBatchResponse> acknowledgeReceipt(
            @PathVariable UUID id,
            @RequestParam boolean accept) {
        return ResponseEntity.ok(dispatchService.acknowledgeReceipt(id, accept));
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'SHOP_MANAGER', 'WORKSHOP_MANAGER')")
    public ResponseEntity<DispatchBatchResponse> cancelDispatch(@PathVariable UUID id) {
        return ResponseEntity.ok(dispatchService.cancelDispatch(id));
    }
}