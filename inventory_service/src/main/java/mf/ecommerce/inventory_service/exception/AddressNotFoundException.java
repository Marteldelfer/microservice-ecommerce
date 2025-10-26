package mf.ecommerce.inventory_service.exception;

import jakarta.persistence.EntityNotFoundException;

public class AddressNotFoundException extends EntityNotFoundException {
    public AddressNotFoundException(String message) {
        super(message);
    }
}
