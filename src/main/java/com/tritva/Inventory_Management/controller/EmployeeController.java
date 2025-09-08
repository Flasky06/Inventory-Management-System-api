package com.tritva.Inventory_Management.controller;

import com.tritva.Inventory_Management.model.dto.EmployeeDto;
import com.tritva.Inventory_Management.model.dto.ShopDto;
import com.tritva.Inventory_Management.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PreAuthorize("hasAnyRole('ADMIN', 'CEO')")
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(employeeService.createEmployee(employeeDto));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new EmployeeDto(null,null,null,null,null,
                            null,null,null, e.getMessage()));
        }
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'CEO')")
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employee = employeeService.listEmployees();
        return ResponseEntity.ok(employee);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CEO')")
    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable UUID employeeId) {
        EmployeeDto employee = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employee);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CEO')")
    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable UUID employeeId,
                                                      @RequestBody EmployeeDto employeeDto) {
        EmployeeDto updatedEmployee = employeeService.updateEmployee(employeeId, employeeDto);
        return ResponseEntity.ok(updatedEmployee);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CEO')")
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteShop(@PathVariable UUID employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }
}
