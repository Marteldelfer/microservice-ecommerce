package mf.ecommerce.product_service.exception;

public class ImageDeletionFailedException extends RuntimeException {
    public ImageDeletionFailedException(String message) {
        super(message);
    }
}
