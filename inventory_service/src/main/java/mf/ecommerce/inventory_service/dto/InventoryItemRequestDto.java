package mf.ecommerce.inventory_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mf.ecommerce.inventory_service.validator.CreateInventoryItemValidationGroup;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class InventoryItemRequestDto {

    @NotNull(message = "ProductId is required", groups = {CreateInventoryItemValidationGroup.class})
    private UUID productId;
    @NotNull(message = "ProviderId is required", groups = {CreateInventoryItemValidationGroup.class})
    private UUID providerId;

    @NotNull(message = "Full price is required", groups = {CreateInventoryItemValidationGroup.class})
    @DecimalMin(value = "0.0", inclusive = false, message = "Full price should be positive", groups = {Default.class})
    private BigDecimal fullPrice;

    @NotNull(message = "Price is required", groups = {CreateInventoryItemValidationGroup.class})
    @DecimalMin(value = "0.0", inclusive = false, message = "Price should be positive", groups = {Default.class})
    private BigDecimal price;

    @NotNull(message = "Quantity is required", groups = {CreateInventoryItemValidationGroup.class})
    @Min(value = 0, message = "Quantity should not be negative", groups = {Default.class})
    private Integer quantity;

}
