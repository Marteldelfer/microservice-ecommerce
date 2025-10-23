package mf.ecommerce.product_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class LinkImageSrcRequestDto {

    @NotNull
    private final UUID productId;
    @NotNull
    private final UUID imageSrcId;
}
