package mf.ecommerce.product_service.mapper;

import mf.ecommerce.product_service.dto.CategoryRequestDto;
import mf.ecommerce.product_service.dto.CategoryResponseDto;
import mf.ecommerce.product_service.model.Category;

public class CategoryMapper {

    public static Category toCategory(CategoryRequestDto dto) {
        return Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public static CategoryResponseDto toDto(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
