package mf.ecommerce.inventory_service.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
public class InventoryItemResponseDto {

    private UUID id;
    private ProductResponseDto product;
    private ProductProviderResponseDto provider;
    private BigDecimal fullPrice;
    private BigDecimal price;
    private Integer quantity;

}
