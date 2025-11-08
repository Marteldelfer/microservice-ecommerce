package mf.ecommerce.auth_service.config;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class KeyCloakConfig {

    @Value("${keycloak.auth-server-url:defaultUrl}")
    private String authServerUrl;
    @Value("${keycloak.realm:defaultRealm}")
    private String realm;
    @Value("${keycloak.resource:defaultResource}")
    private String clientId;
    @Value("${keycloak.credentials.secret:defaultSecret}")
    private String clientSecret;

    @Bean
    public Keycloak keycloakAdminClient() {
        log.info("Creating Keycloak Admin Client with {}, {}, {}, {}", authServerUrl, realm, clientId, clientSecret);
        return KeycloakBuilder.builder()
                .serverUrl(authServerUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
    }
}
