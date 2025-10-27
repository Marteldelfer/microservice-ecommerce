package mf.ecommerce.inventory_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<InventoryItem> inventoryItems;
}
