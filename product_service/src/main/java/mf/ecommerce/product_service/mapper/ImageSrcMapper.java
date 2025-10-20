package mf.ecommerce.product_service.mapper;

import mf.ecommerce.product_service.dto.ImageSrcRequest;
import mf.ecommerce.product_service.dto.ImageSrcResponseDto;
import mf.ecommerce.product_service.model.ImageSrc;

public class ImageSrcMapper {

    public static ImageSrcResponseDto toDto(ImageSrc imageSrc) {
        return ImageSrcResponseDto.builder()
                .id(imageSrc.getId())
                .url(imageSrc.getUrl())
                .slug(imageSrc.getSlug())
                .altText(imageSrc.getAltText())
                .orderIndex(imageSrc.getOrderIndex())
                .build();
    }

    public static ImageSrc toEntity(ImageSrcRequest dto) {
        return ImageSrc.builder()
                .altText(dto.getAltText())
                .orderIndex(dto.getOrderIndex())
                .build(); // Image upload implemented in service layer
    }
}
