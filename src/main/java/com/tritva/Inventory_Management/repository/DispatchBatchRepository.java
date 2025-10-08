package com.tritva.Inventory_Management.repository;

import com.tritva.Inventory_Management.model.DispatchStatus;
import com.tritva.Inventory_Management.model.entity.DispatchBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DispatchBatchRepository extends JpaRepository<DispatchBatch, UUID> {
    Optional<DispatchBatch> findByDispatchReference(String dispatchReference);
    List<DispatchBatch> findBySourceShopId(UUID sourceShopId);
    List<DispatchBatch> findByDestinationShopId(UUID destinationShopId);
}
