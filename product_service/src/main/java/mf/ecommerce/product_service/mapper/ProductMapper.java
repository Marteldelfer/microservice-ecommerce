package mf.ecommerce.product_service.mapper;

import mf.ecommerce.product_service.dto.ProductRequestDto;
import mf.ecommerce.product_service.dto.ProductResponseDto;
import mf.ecommerce.product_service.kafka.ProductEvent;
import mf.ecommerce.product_service.kafka.ProductEventType;
import mf.ecommerce.product_service.model.Category;
import mf.ecommerce.product_service.model.Product;
import mf.ecommerce.product_service.model.Tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductResponseDto toDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .mainImage(product.getMainImage())
                .tags(product.getTags().stream().map(TagMapper::toDto).collect(Collectors.toSet()))
                .category(product.getCategories().stream().map(CategoryMapper::toDto).collect(Collectors.toSet()))
                .images(product.getImages().stream().map(ImageSrcMapper::toDto).collect(Collectors.toSet()))
                .build();
    }

    public static Product toEntity(ProductRequestDto dto) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .images(new ArrayList<>())
                .tags(new HashSet<>())
                .categories(new HashSet<>())
                .build();
    }

    public static ProductEvent toEvent(Product product, ProductEventType eventType) {
        return ProductEvent.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .mainImage(product.getMainImage())
                .active(product.getIsActive())
                .updatedAt(product.getUpdatedAt())
                .tags(product.getTags().stream().map(Tag::getName).collect(Collectors.toSet()))
                .categories(product.getCategories().stream().map(Category::getName).collect(Collectors.toSet()))
                .type(eventType)
                .build();
    }

    public static void update(Product product, ProductRequestDto dto) {
        if (dto.getName() != null) {product.setName(dto.getName());}
        if (dto.getDescription() != null) {product.setDescription(dto.getDescription());}
    }
}
