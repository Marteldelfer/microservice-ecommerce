package mf.ecommerce.inventory_service.exception;

public class InvalidEventTypeException extends RuntimeException {
    public InvalidEventTypeException(String message) {
        super(message);
    }
}
