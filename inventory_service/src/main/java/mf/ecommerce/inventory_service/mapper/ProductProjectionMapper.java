package mf.ecommerce.inventory_service.mapper;

import mf.ecommerce.inventory_service.kafka.ProductEvent;
import mf.ecommerce.inventory_service.model.ProductProjection;

public class ProductProjectionMapper {

    public static ProductProjection toProductProjection(ProductEvent productEvent) {
        return ProductProjection.builder()
                .id(productEvent.getId())
                .name(productEvent.getName())
                .description(productEvent.getDescription())
                .mainImage(productEvent.getMainImage())
                .active(productEvent.getActive())
                .updatedAt(productEvent.getUpdatedAt())
                .build();
    }

}
