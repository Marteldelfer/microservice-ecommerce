package mf.ecommerce.product_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class LinkCategoryRequestDto {

    @NotNull(message = "product id is required")
    private final UUID productId;
    @NotNull(message = "category id is required")
    private final UUID categoryId;
}
