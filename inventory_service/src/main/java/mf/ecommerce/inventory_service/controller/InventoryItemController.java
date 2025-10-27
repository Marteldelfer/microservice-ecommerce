package mf.ecommerce.inventory_service.controller;

import lombok.AllArgsConstructor;
import mf.ecommerce.inventory_service.dto.InventoryItemRequestDto;
import mf.ecommerce.inventory_service.dto.InventoryItemResponseDto;
import mf.ecommerce.inventory_service.service.InventoryItemService;
import mf.ecommerce.inventory_service.validator.CreateInventoryItemValidationGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/inventory")
@AllArgsConstructor
public class InventoryItemController {

    private final InventoryItemService inventoryItemService;

    @GetMapping("/{id}")
    public ResponseEntity<InventoryItemResponseDto> getInventoryItem(@PathVariable UUID id) {
        return ResponseEntity.ok(inventoryItemService.getInventoryItem(id));
    }

    @GetMapping
    public ResponseEntity<List<InventoryItemResponseDto>> getInventoryItems() {
        return ResponseEntity.ok(inventoryItemService.getInventoryItems());
    }

    @PostMapping
    public ResponseEntity<InventoryItemResponseDto> createInventoryItem(
            @Validated({CreateInventoryItemValidationGroup.class}) @RequestBody InventoryItemRequestDto dto
    ) {
        return ResponseEntity.ok(inventoryItemService.createInventoryItem(dto));
    }


}
