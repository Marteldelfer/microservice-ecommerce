package mf.ecommerce.product_service.exception;

public class TagWithNameAlreadyExistsException extends RuntimeException {
    public TagWithNameAlreadyExistsException(String message) {
        super(message);
    }
}
