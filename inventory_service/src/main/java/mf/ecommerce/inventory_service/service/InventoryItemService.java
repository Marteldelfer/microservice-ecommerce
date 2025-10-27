package mf.ecommerce.inventory_service.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mf.ecommerce.inventory_service.dto.InventoryItemRequestDto;
import mf.ecommerce.inventory_service.dto.InventoryItemResponseDto;
import mf.ecommerce.inventory_service.exception.InventoryItemNotFoundException;
import mf.ecommerce.inventory_service.mapper.InventoryItemMapper;
import mf.ecommerce.inventory_service.model.InventoryItem;
import mf.ecommerce.inventory_service.model.ProductProjection;
import mf.ecommerce.inventory_service.model.ProductProvider;
import mf.ecommerce.inventory_service.repository.InventoryItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class InventoryItemService {

    private final InventoryItemRepository inventoryItemRepository;
    private final ProductProviderService productProviderService;
    private final ProductProjectionService productProjectionService;

    public InventoryItemResponseDto getInventoryItem(UUID id) {
        return InventoryItemMapper.toDto(inventoryItemRepository.findById(id).orElseThrow(
                () -> new InventoryItemNotFoundException("InventoryItem with id " + id + " not found")
        ));
    }

    public List<InventoryItemResponseDto> getInventoryItems() {
        return inventoryItemRepository.findAll().stream().map(InventoryItemMapper::toDto).toList();
    }

    public InventoryItemResponseDto createInventoryItem(InventoryItemRequestDto dto) {
        ProductProjection product = productProjectionService.getById(dto.getProductId());
        ProductProvider provider = productProviderService.getProductProviderEntity(dto.getProviderId());

        InventoryItem inventoryItem = inventoryItemRepository.save(InventoryItemMapper.toEntity(dto, product, provider));
        productProjectionService.linkInventoryItem(dto.getProductId(), inventoryItem);
        productProviderService.linkInventoryItem(dto.getProviderId(), inventoryItem);
        return InventoryItemMapper.toDto(inventoryItemRepository.save(inventoryItem));
    }
}
