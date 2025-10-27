package mf.ecommerce.inventory_service.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class ProductResponseDto {

    private UUID id;
    private String name;
    private String description;
    private String mainImage;

}
