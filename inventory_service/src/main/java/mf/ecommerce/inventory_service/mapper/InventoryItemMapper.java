package mf.ecommerce.inventory_service.mapper;

import mf.ecommerce.inventory_service.dto.InventoryItemRequestDto;
import mf.ecommerce.inventory_service.dto.InventoryItemResponseDto;
import mf.ecommerce.inventory_service.model.InventoryItem;
import mf.ecommerce.inventory_service.model.ProductProjection;
import mf.ecommerce.inventory_service.model.ProductProvider;

public class InventoryItemMapper {

    public static InventoryItemResponseDto toDto(InventoryItem item) {
        return InventoryItemResponseDto.builder()
                .id(item.getId())
                .quantity(item.getQuantity())
                .fullPrice(item.getFullPrice())
                .price(item.getPrice())
                .product(ProductProjectionMapper.toDto(item.getProduct()))
                .provider(ProductProviderMapper.toDto(item.getProvider()))
                .build();
    }

    public static InventoryItem toEntity(InventoryItemRequestDto dto, ProductProjection product, ProductProvider provider) {
        return InventoryItem.builder()
                .quantity(dto.getQuantity())
                .reserved(0)
                .fullPrice(dto.getFullPrice())
                .price(dto.getPrice())
                .product(product)
                .provider(provider)
                .build();
    }

    public static void update(InventoryItem item, InventoryItemRequestDto dto) {
        if (dto.getPrice() != null) {item.setPrice(dto.getPrice());}
        if (dto.getQuantity() != null) {item.setQuantity(dto.getQuantity());}
        if (dto.getFullPrice() != null) {item.setFullPrice(dto.getFullPrice());}
    }
}
