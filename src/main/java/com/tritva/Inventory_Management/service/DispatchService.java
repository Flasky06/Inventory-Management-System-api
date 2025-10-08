package com.tritva.Inventory_Management.service;

import com.tritva.Inventory_Management.model.dto.CreateDispatchRequest;
import com.tritva.Inventory_Management.model.dto.DispatchBatchResponse;

import java.util.List;
import java.util.UUID;

public interface DispatchService {
    DispatchBatchResponse createDispatch(CreateDispatchRequest request);
    List<DispatchBatchResponse> getAllDispatches();
    DispatchBatchResponse getDispatchById(UUID id);
    DispatchBatchResponse getDispatchByReference(String referenceNumber);
    List<DispatchBatchResponse> getDispatchesBySourceShop(UUID shopId);
    List<DispatchBatchResponse> getDispatchesByDestinationShop(UUID shopId);
    DispatchBatchResponse acknowledgeReceipt(UUID dispatchId, boolean accept);
    DispatchBatchResponse cancelDispatch(UUID dispatchId);

}