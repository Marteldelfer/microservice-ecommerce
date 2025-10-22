package mf.ecommerce.product_service.exception;

public class CategoryWithNameAlreadyExistsException extends RuntimeException {
    public CategoryWithNameAlreadyExistsException(String message) {
        super(message);
    }
}
