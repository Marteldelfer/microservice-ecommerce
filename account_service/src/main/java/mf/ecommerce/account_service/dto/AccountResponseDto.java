package mf.ecommerce.account_service.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class AccountResponseDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;

}
