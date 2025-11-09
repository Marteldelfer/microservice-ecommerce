package mf.ecommerce.auth_service.exception;

public class KeycloakTokenCreationException extends RuntimeException {
    public KeycloakTokenCreationException(String message) {
        super(message);
    }
}
