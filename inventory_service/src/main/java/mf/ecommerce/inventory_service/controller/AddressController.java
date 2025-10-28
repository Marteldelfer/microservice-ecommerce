package mf.ecommerce.inventory_service.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import mf.ecommerce.inventory_service.dto.AddressRequestDto;
import mf.ecommerce.inventory_service.dto.AddressResponseDto;
import mf.ecommerce.inventory_service.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Address", description = "API for reading and updating addresses")
@RestController
@RequestMapping("/addresses")
@AllArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/{id}")
    @Operation(summary = "Get address by id")
    public ResponseEntity<AddressResponseDto> getAddress(@PathVariable UUID id) {
        return ResponseEntity.ok(addressService.getAddress(id));
    }

    @GetMapping
    @Operation(summary = "Get all addresses")
    public ResponseEntity<List<AddressResponseDto>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update address")
    public ResponseEntity<AddressResponseDto> updateAddress(
            @PathVariable UUID id,
            @Validated({Default.class}) @RequestBody AddressRequestDto dto
    ) {
        return ResponseEntity.ok(addressService.updateAddress(id, dto));
    }
}
