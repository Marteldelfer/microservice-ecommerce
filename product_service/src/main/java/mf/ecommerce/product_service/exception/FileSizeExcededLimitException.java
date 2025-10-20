package mf.ecommerce.product_service.exception;

public class FileSizeExcededLimitException extends RuntimeException {
    public FileSizeExcededLimitException(String message) {
        super(message);
    }
}
