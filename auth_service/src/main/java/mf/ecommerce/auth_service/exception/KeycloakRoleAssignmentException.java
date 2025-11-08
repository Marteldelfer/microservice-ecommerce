package mf.ecommerce.auth_service.exception;

public class KeycloakRoleAssignmentException extends RuntimeException {
    public KeycloakRoleAssignmentException(String message) {
        super(message);
    }
}
