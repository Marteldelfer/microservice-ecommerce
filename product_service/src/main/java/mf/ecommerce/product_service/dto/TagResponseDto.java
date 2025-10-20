package mf.ecommerce.product_service.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public class TagResponseDto {

    private UUID id;
    private String name;
    private String description;
}
