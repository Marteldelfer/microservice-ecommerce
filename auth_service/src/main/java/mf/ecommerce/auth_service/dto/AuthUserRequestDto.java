package mf.ecommerce.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class AuthUserRequestDto {

    @NotNull(message = "Id is required")
    private UUID id;
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be properly formated")
    private String email;
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Role is required")
    @Pattern(regexp = "CUSTOMER|PROVIDER|ADMIN", message = "Role must be either CUSTOMER, PROVIDER or ADMIN")
    private String role;

}