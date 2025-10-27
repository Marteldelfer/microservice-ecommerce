package mf.ecommerce.product_service.mapper;

import mf.ecommerce.product_service.dto.TagRequestDto;
import mf.ecommerce.product_service.dto.TagResponseDto;
import mf.ecommerce.product_service.model.Tag;

public class TagMapper {

    public static Tag toEntity(TagRequestDto dto) {
        return Tag.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }
    public static TagResponseDto toDto(Tag tag) {
        return TagResponseDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .description(tag.getDescription())
                .build();
    }

    public static void update(Tag tag, TagRequestDto dto) {
        if (dto.getName() != null) {tag.setName(tag.getName());}
        if (dto.getDescription() != null) {tag.setDescription(tag.getDescription());}
    }
}
