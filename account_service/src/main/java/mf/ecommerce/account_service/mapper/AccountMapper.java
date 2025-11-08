package mf.ecommerce.account_service.mapper;

import mf.ecommerce.account_service.dto.AccountRequestDto;
import mf.ecommerce.account_service.dto.AccountResponseDto;
import mf.ecommerce.account_service.dto.AuthRequestDto;
import mf.ecommerce.account_service.kafka.AccountEmailEvent;
import mf.ecommerce.account_service.model.Account;

import java.util.ArrayList;

public class AccountMapper {

    public static AccountResponseDto toDto(Account account) {
        return AccountResponseDto.builder()
                .firstName(account.getFirstname())
                .lastName(account.getLastname())
                .email(account.getEmail())
                .id(account.getId())
                .build();
    }

    public static Account toEntity(AccountRequestDto dto, Account.AccountRole role) {
        return Account.builder()
                .firstname(dto.getFirstName())
                .lastname(dto.getLastName())
                .email(dto.getEmail())
                .role(role)
                .verified(false)
                .addresses(new ArrayList<>())
                .build();
    }

    public static AuthRequestDto toAuthRequest(Account account, String password) {
        return AuthRequestDto.builder()
                .id(account.getId())
                .email(account.getEmail())
                .firstName(account.getFirstname())
                .lastName(account.getLastname())
                .role(account.getRole().toString())
                .password(password)
                .build();
    }

    public static AccountEmailEvent toEmailEvent(Account account, String jwt) {
        return AccountEmailEvent.builder()
                .id(account.getId())
                .firstName(account.getFirstname())
                .lastName(account.getLastname())
                .email(account.getEmail())
                .jwt(jwt)
                .build();
    }
}
