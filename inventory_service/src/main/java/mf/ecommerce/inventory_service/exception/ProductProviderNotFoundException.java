package mf.ecommerce.inventory_service.exception;

import jakarta.persistence.EntityNotFoundException;

public class ProductProviderNotFoundException extends EntityNotFoundException {
    public ProductProviderNotFoundException(String message) {super(message);}
}
