package mf.ecommerce.product_service.kafka;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ProductEvent {

    private UUID id;
    private String name;
    private String description;
    private String mainImage;
    private Boolean active;
    private LocalDateTime updatedAt;

    private Set<String> tags;
    private Set<String> categories;
    private ProductEventType type;

}
