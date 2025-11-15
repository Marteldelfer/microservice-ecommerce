package mf.ecommerce.auth_service.exception;

public class KeycloakOAuthTokenCreationException extends RuntimeException {
    public KeycloakOAuthTokenCreationException(String message) {
        super(message);
    }
}
