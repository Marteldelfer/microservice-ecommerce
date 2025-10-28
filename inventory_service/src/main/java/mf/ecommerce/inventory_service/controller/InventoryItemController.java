package mf.ecommerce.inventory_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import mf.ecommerce.inventory_service.dto.InventoryItemRequestDto;
import mf.ecommerce.inventory_service.dto.InventoryItemResponseDto;
import mf.ecommerce.inventory_service.service.InventoryItemService;
import mf.ecommerce.inventory_service.validators.CreateValidationGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/inventory")
@AllArgsConstructor
@Tag(name = "Inventory", description = "API for managing inventory")
public class InventoryItemController {

    private final InventoryItemService inventoryItemService;

    @GetMapping("/{id}")
    @Operation(summary = "Get inventory item by id")
    public ResponseEntity<InventoryItemResponseDto> getInventoryItem(@PathVariable UUID id) {
        return ResponseEntity.ok(inventoryItemService.getInventoryItem(id));
    }

    @GetMapping
    @Operation(summary = "Get all inventory items")
    public ResponseEntity<List<InventoryItemResponseDto>> getInventoryItems() {
        return ResponseEntity.ok(inventoryItemService.getInventoryItems());
    }

    @PostMapping
    @Operation(summary = "Create new inventory item")
    public ResponseEntity<InventoryItemResponseDto> createInventoryItem(
            @Validated({CreateValidationGroup.class, Default.class}) @RequestBody InventoryItemRequestDto dto
    ) {
        return ResponseEntity.ok(inventoryItemService.createInventoryItem(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update inventory item")
    public ResponseEntity<InventoryItemResponseDto> updateInventoryItem(
            @PathVariable UUID id,
            @Validated({Default.class}) @RequestBody InventoryItemRequestDto dto
    ) {
        return ResponseEntity.ok(inventoryItemService.updateInventoryItem(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Inventory item by id")
    public ResponseEntity<InventoryItemResponseDto> deleteInventoryItem(@PathVariable UUID id) {
        inventoryItemService.deleteInventoryItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/product/id/{id}")
    @Operation(summary = "Get all inventory items by product id")
    public ResponseEntity<List<InventoryItemResponseDto>> getInventoryItemsByProductId(@PathVariable UUID id) {
        return ResponseEntity.ok(inventoryItemService.getInventoryItemByProductId(id));
    }

    @GetMapping("/product/name/{name}")
    @Operation(summary = "Get all inventory items by product name")
    public ResponseEntity<List<InventoryItemResponseDto>> getInventoryItemsByProductId(@PathVariable String name) {
        return ResponseEntity.ok(inventoryItemService.getInventoryItemByProductName(name));
    }

}
