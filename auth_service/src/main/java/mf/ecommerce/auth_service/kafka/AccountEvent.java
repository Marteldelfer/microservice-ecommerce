package mf.ecommerce.auth_service.kafka;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountEvent {

    private String id;
    private String email;
    private String firstName;
    private String lastName;
}
