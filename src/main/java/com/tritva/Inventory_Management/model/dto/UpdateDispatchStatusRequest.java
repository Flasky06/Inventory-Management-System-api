package com.tritva.Inventory_Management.model.dto;

import com.tritva.Inventory_Management.model.DispatchStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDispatchStatusRequest {
    private DispatchStatus status;
}
