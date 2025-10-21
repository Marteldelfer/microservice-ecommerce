package mf.ecommerce.product_service.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class ImageSrcResponseDto {

    private UUID id;
    private String url;
    private String slug;
    private String altText;
    private int orderIndex;
}
