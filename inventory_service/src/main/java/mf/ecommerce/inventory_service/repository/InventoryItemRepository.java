package mf.ecommerce.inventory_service.repository;

import mf.ecommerce.inventory_service.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, UUID> {
}
