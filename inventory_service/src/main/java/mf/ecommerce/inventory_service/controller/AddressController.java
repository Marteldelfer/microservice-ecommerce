package mf.ecommerce.inventory_service.controller;


import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import mf.ecommerce.inventory_service.dto.AddressRequestDto;
import mf.ecommerce.inventory_service.dto.AddressResponseDto;
import mf.ecommerce.inventory_service.service.AddressService;
import mf.ecommerce.inventory_service.validator.CreateAddressValidationGroup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/address")
@AllArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDto> getAddress(@PathVariable UUID id) {
        return ResponseEntity.ok(addressService.getAddress(id));
    }

    @GetMapping
    public ResponseEntity<List<AddressResponseDto>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }

    @PostMapping
    public ResponseEntity<AddressResponseDto> createAddress(
            @Validated({CreateAddressValidationGroup.class, Default.class}) @RequestBody AddressRequestDto dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.createAddress(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDto> updateAddress(
            @PathVariable UUID id,
            @Validated({Default.class}) @RequestBody AddressRequestDto dto
    ) {
        return ResponseEntity.ok(addressService.updateAddress(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

}
