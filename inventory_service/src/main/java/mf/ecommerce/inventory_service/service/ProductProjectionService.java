package mf.ecommerce.inventory_service.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mf.ecommerce.inventory_service.exception.ProductProjectionNotFoundException;
import mf.ecommerce.inventory_service.model.InventoryItem;
import mf.ecommerce.inventory_service.model.ProductProjection;
import mf.ecommerce.inventory_service.repository.ProductProjectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ProductProjectionService {

    private final ProductProjectionRepository projectionRepository;

    public ProductProjection getById(UUID id) {
        return projectionRepository.findById(id).orElseThrow(
                () -> new ProductProjectionNotFoundException("ProductProjection with id " + id + " not found")
        );
    }

    public List<ProductProjection> getAll() {
        return projectionRepository.findAll();
    }

    @Transactional
    public void linkInventoryItem(UUID productId, InventoryItem inventoryItem) {
        ProductProjection projection = getById(productId);
        projection.getInventoryItems().add(inventoryItem);
        projectionRepository.save(projection);
    }

    @Transactional
    public void unlinkInventoryItem(UUID id, InventoryItem inventoryItem) {
        ProductProjection projection = getById(id);
        projection.getInventoryItems().remove(inventoryItem);
        projectionRepository.save(projection);
    }
}
