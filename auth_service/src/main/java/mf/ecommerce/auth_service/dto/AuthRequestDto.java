package mf.ecommerce.auth_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class AuthRequestDto {

    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String role;

}