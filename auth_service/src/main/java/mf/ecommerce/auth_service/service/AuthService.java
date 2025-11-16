package mf.ecommerce.auth_service.service;

import jakarta.ws.rs.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import mf.ecommerce.auth_service.dto.LoginRequestDto;
import mf.ecommerce.auth_service.exception.KeycloakOAuthTokenCreationException;
import mf.ecommerce.auth_service.exception.KeycloakTokenCreationException;
import mf.ecommerce.auth_service.kafka.AccountEventProducer;
import mf.ecommerce.auth_service.util.JwtClaimsMapper;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
public class AuthService {

    private final String authServerUrl;
    private final String realm;
    private final String clientId;
    private final String clientSecret;
    private final RestClient restClient;
    private final AccountEventProducer accountEventProducer;

    public AuthService(
            @Value("${keycloak.auth-server-url:defaultUrl}") String authServerUrl,
            @Value("${keycloak.realm:defaultRealm}") String realm,
            @Value("${keycloak.resource:defaultResource}") String clientId,
            @Value("${keycloak.credentials.secret:defaultSecret}") String clientSecret,
            AccountEventProducer accountEventProducer
    ) {
        this.authServerUrl = authServerUrl;
        this.realm = realm;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.restClient = RestClient.builder()
                .baseUrl(authServerUrl)
                .build();
        this.accountEventProducer = accountEventProducer;
    }

    public String createLocalToken(LoginRequestDto dto) {
        try {
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(authServerUrl)
                    .realm(realm)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .grantType(OAuth2Constants.PASSWORD)
                    .username(dto.getEmail())
                    .password(dto.getPassword())
                    .build();
            AccessTokenResponse tokenResponse = keycloak.tokenManager().getAccessToken();
            return tokenResponse.getToken();
        } catch (BadRequestException e) {
            log.error(e.getResponse().readEntity(String.class));
            throw new KeycloakTokenCreationException("Could not create jwt token: " + e.getMessage());
        }
    }

    public String createOAuth2Token(String code, String redirectUri) {
        try {
            log.info("Attempting to create OAuth2 token");
            MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
            form.add("grant_type", "authorization_code");
            form.add("client_id", clientId);
            form.add("client_secret", clientSecret);
            form.add("code", code);
            form.add("redirect_uri", redirectUri);

            AccessTokenResponse response = restClient.post()
                    .uri("realms/" + realm + "/protocol/openid-connect/token")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(form)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (_, e) -> {
                        throw new KeycloakOAuthTokenCreationException("Could not create OAuth2 token: " + e.getStatusText());
                    })
                    .toEntity(AccessTokenResponse.class)
                    .getBody();

            if (response != null) {
                String token = response.getToken();
                accountEventProducer.sendAccountCreationEvent(JwtClaimsMapper.toEvent(token));
                return token;
            } else {
                throw new KeycloakOAuthTokenCreationException("Token response was null");
            }
        } catch (BadRequestException e) {
            throw new KeycloakTokenCreationException("Could not create jwt token from oauth user: " + e.getMessage());
        }
    }
}
