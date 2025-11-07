package mf.ecommerce.account_service.kafka;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class AccountEmailEvent {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String jwt; // Validation Jwt

}
