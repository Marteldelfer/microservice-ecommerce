package mf.ecommerce.inventory_service.repository;

import mf.ecommerce.inventory_service.model.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductProjectionRepository extends JpaRepository<ProductProjection, UUID> {
}
