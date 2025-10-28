package mf.ecommerce.inventory_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Provider", description = "API for managing product providers")
public class ProductProviderController {

    private final ProductProviderService productProviderService;

    @GetMapping("/{id}")
    @Operation(summary = "Get product provider by id")
    public ResponseEntity<ProductProviderResponseDto> getProvider(@PathVariable UUID id) {
        return ResponseEntity.ok(productProviderService.getProductProvider(id));
    }

    @GetMapping
    @Operation(summary = "Get all product providers")
    public ResponseEntity<List<ProductProviderResponseDto>> getAllProviders() {
        return ResponseEntity.ok(productProviderService.getAllProductProviders());
    }

    @PostMapping
    @Operation(summary = "Create new product provider")
    public ResponseEntity<ProductProviderResponseDto> createProvider(
            @Validated({Default.class, CreateValidationGroup.class}) @RequestBody ProductProviderRequestDto dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productProviderService.createProductProvider(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product provider")
    public ResponseEntity<ProductProviderResponseDto> updateProvider(
            @PathVariable UUID id,
            @Validated({Default.class})  @RequestBody ProductProviderRequestDto dto
    ) {
        return ResponseEntity.ok(productProviderService.updateProductProvider(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product provider by id")
    public ResponseEntity<Void> deleteProvider(@PathVariable UUID id) {
        productProviderService.deleteProductProvider(id);
        return ResponseEntity.noContent().build();
    }

}
