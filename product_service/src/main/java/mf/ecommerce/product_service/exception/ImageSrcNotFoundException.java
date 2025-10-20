package mf.ecommerce.product_service.exception;

import jakarta.persistence.EntityNotFoundException;

public class ImageSrcNotFoundException extends EntityNotFoundException {
    public ImageSrcNotFoundException(String message) {
        super(message);
    }
}
