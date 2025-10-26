package mf.ecommerce.inventory_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductProjection {

    @Id
    private UUID id;

    private String name;
    private String description;
    private String mainImage;
    private Boolean active;
    private LocalDateTime updatedAt;
}
