package mf.ecommerce.auth_service.exception;

public class JwtParsingException extends RuntimeException {
    public JwtParsingException(String message) {
        super(message);
    }
}
