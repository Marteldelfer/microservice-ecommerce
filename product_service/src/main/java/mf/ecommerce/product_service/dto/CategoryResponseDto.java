package mf.ecommerce.product_service.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class CategoryResponseDto {

    private UUID id;
    private String name;
    private String description;
}
