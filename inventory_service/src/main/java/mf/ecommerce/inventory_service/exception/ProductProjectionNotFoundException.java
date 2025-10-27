package mf.ecommerce.inventory_service.exception;

import jakarta.persistence.EntityNotFoundException;

public class ProductProjectionNotFoundException extends EntityNotFoundException {
    public ProductProjectionNotFoundException(String s) {
        super(s);
    }
}
