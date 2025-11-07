package mf.ecommerce.account_service.exception;

public class AccountWithEmailAlreadyExists extends RuntimeException {
    public AccountWithEmailAlreadyExists(String message) {
        super(message);
    }
}
