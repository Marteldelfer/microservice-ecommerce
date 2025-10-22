package mf.ecommerce.product_service.mapper;

import mf.ecommerce.product_service.dto.ProductRequestDto;
import mf.ecommerce.product_service.dto.ProductResponseDto;
import mf.ecommerce.product_service.model.Category;
import mf.ecommerce.product_service.model.ImageSrc;
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

    public static Product toProduct(ProductRequestDto dto) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .images(new ArrayList<>())
                .tags(new HashSet<>())
                .categories(new HashSet<>())
                .build();
    }

}
