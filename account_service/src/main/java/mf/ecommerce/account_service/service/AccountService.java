package mf.ecommerce.account_service.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import mf.ecommerce.account_service.dto.AccountRequestDto;
import mf.ecommerce.account_service.dto.AccountResponseDto;
import mf.ecommerce.account_service.dto.AuthRequestDto;
import mf.ecommerce.account_service.exception.AccountNotFoundException;
import mf.ecommerce.account_service.exception.AccountWithEmailAlreadyExists;
import mf.ecommerce.account_service.exception.FailedToCreateAuthUserException;
import mf.ecommerce.account_service.kafka.AccountEventProducer;
import mf.ecommerce.account_service.mapper.AccountMapper;
import mf.ecommerce.account_service.model.Account;
import mf.ecommerce.account_service.repository.AccountRepository;
import mf.ecommerce.account_service.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Slf4j
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountEventProducer eventProducer;
    private final JwtUtil jwtUtil;
    private final RestClient restClient;

    public AccountService(
            AccountRepository accountRepository,
            AccountEventProducer eventProducer,
            JwtUtil jwtUtil,
            @Value("${auth.service.url:default}") String authServiceUrl
    ) {
        if (authServiceUrl.equals("default")) {log.warn("Auth service url is missing. Set environment variable");}
        this.accountRepository = accountRepository;
        this.eventProducer = eventProducer;
        this.jwtUtil = jwtUtil;
        this.restClient = RestClient.builder()
                .baseUrl(authServiceUrl)
                .build();
    }

    public AccountResponseDto getById(UUID id) {
        return AccountMapper.toDto(accountRepository.findById(id).orElseThrow(
                () -> new AccountNotFoundException("Account with id " + id + " not found")
        ));
    }

    @Transactional
    public AccountResponseDto createAccount(AccountRequestDto dto) {
        log.info("Creating account with email {}", dto.getEmail());
        if (accountRepository.existsByEmail(dto.getEmail())) {
            throw new AccountWithEmailAlreadyExists("Account with email " + dto.getEmail() + " already exists");
        }
        Account account = accountRepository.save(AccountMapper.toEntity(dto, Account.AccountRole.CUSTOMER));

        createAuthUser(AccountMapper.toAuthRequest(account, dto.getPassword()));
        String jwt = jwtUtil.createValidationJwt(account);
        eventProducer.sendValidationEmailEvent(account, jwt);

        return AccountMapper.toDto(account);
    }

    private void createAuthUser(AuthRequestDto dto) {
        log.info("Sending auth user request with id {}", dto.getId());
        Boolean created = restClient.post()
                .uri("/accounts")
                .body(dto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (_, _) -> {
                    throw new FailedToCreateAuthUserException("Failed to create auth user with id " + dto.getId());
                })
                .onStatus(HttpStatusCode::is5xxServerError, (_, _) -> {
                    throw new FailedToCreateAuthUserException("Failed to create auth user with id " + dto.getId());
                })
                .body(Boolean.class);
        if (Boolean.FALSE.equals(created)) {
            throw new FailedToCreateAuthUserException("Failed to create auth user with id " + dto.getId());
        }
    }

}
