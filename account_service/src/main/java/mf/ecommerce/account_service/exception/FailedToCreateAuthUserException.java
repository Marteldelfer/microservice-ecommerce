package mf.ecommerce.account_service.exception;

public class FailedToCreateAuthUserException extends RuntimeException {
    public FailedToCreateAuthUserException(String message) {
        super(message);
    }
}
