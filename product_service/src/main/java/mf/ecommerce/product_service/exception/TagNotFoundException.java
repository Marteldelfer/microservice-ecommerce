package mf.ecommerce.product_service.exception;

import jakarta.persistence.EntityNotFoundException;

public class TagNotFoundException extends EntityNotFoundException {
    public TagNotFoundException(String message) {
        super(message);
    }
}
