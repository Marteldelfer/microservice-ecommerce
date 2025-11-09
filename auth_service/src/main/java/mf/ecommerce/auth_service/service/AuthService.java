package mf.ecommerce.auth_service.service;

import jakarta.ws.rs.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import mf.ecommerce.auth_service.dto.LoginRequestDto;
import mf.ecommerce.auth_service.exception.KeycloakTokenCreationException;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    @Value("${keycloak.auth-server-url:defaultUrl}")
    private String authServerUrl;
    @Value("${keycloak.realm:defaultRealm}")
    private String realm;
    @Value("${keycloak.resource:defaultResource}")
    private String clientId;
    @Value("${keycloak.credentials.secret:defaultSecret}")
    private String clientSecret;

    public String createToken(LoginRequestDto dto) {
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
}
