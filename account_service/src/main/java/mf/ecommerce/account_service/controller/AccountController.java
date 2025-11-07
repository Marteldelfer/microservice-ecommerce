package mf.ecommerce.account_service.controller;

import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import mf.ecommerce.account_service.dto.AccountRequestDto;
import mf.ecommerce.account_service.dto.AccountResponseDto;
import mf.ecommerce.account_service.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getAccount(@PathVariable UUID id) {
        return ResponseEntity.ok(accountService.getById(id));
    }

    @PostMapping
    public ResponseEntity<AccountResponseDto> createAccount(@Validated({Default.class}) @RequestBody AccountRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(dto));
    }

}
