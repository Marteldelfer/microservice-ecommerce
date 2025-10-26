package mf.ecommerce.inventory_service.repository;

import mf.ecommerce.inventory_service.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
