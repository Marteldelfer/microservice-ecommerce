package mf.ecommerce.inventory_service.controller;

import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import mf.ecommerce.inventory_service.dto.ProductProviderRequestDto;
import mf.ecommerce.inventory_service.dto.ProductProviderResponseDto;
import mf.ecommerce.inventory_service.service.ProductProviderService;
import mf.ecommerce.inventory_service.validators.CreateValidationGroup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/providers")
@AllArgsConstructor
public class ProductProviderController {

    private final ProductProviderService productProviderService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductProviderResponseDto> getProvider(@PathVariable UUID id) {
        return ResponseEntity.ok(productProviderService.getProductProvider(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductProviderResponseDto>> getAllProviders() {
        return ResponseEntity.ok(productProviderService.getAllProductProviders());
    }

    @PostMapping
    public ResponseEntity<ProductProviderResponseDto> createProvider(
            @Validated({Default.class, CreateValidationGroup.class}) @RequestBody ProductProviderRequestDto dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productProviderService.createProductProvider(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductProviderResponseDto> updateProvider(
            @PathVariable UUID id,
            @Validated({Default.class})  @RequestBody ProductProviderRequestDto dto
    ) {
        return ResponseEntity.ok(productProviderService.updateProductProvider(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable UUID id) {
        productProviderService.deleteProductProvider(id);
        return ResponseEntity.noContent().build();
    }

}
