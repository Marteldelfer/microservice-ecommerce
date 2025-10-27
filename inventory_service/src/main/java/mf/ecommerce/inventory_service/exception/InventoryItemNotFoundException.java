package mf.ecommerce.inventory_service.exception;

import jakarta.persistence.EntityNotFoundException;

public class InventoryItemNotFoundException extends EntityNotFoundException {
    public InventoryItemNotFoundException(String message) {
        super(message);
    }
}
