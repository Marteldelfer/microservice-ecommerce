package mf.ecommerce.auth_service.exception;

public class KeycloakUserCreationException extends RuntimeException {
    public KeycloakUserCreationException(String message) {
        super(message);
    }
}
