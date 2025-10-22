package mf.ecommerce.product_service.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Builder
@Getter
public class ProductResponseDto {

    private UUID id;
    private String name;
    private String description;

    private String mainImage;
    private Set<ImageSrcResponseDto> images;

    private Set<CategoryResponseDto> category;
    private Set<TagResponseDto> tags;
}
