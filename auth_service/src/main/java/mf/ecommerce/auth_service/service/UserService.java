package mf.ecommerce.auth_service.service;

import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import mf.ecommerce.auth_service.dto.AuthUserRequestDto;
import mf.ecommerce.auth_service.exception.KeycloakPasswordAssignmentException;
import mf.ecommerce.auth_service.exception.KeycloakRoleAssignmentException;
import mf.ecommerce.auth_service.exception.KeycloakUserCreationException;
import mf.ecommerce.auth_service.exception.KeycloakUserNotFoundException;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class UserService {

    private final Keycloak keycloakClient;
    private final String realm;

    public UserService(Keycloak keycloakClient, @Value("${keycloak.realm:defaultRealm}") String realm) {
        this.keycloakClient = keycloakClient;
        this.realm = realm;
    }

    public void createKeycloakUser(AuthUserRequestDto dto) {
        log.info("Creating keycloak user with email {}", dto.getEmail());
        RealmResource realmResource = keycloakClient.realm(realm);
        UsersResource usersResource = realmResource.users();

        UserRepresentation user = createUserRepresentation(dto);

        Response response = usersResource.create(user);
        var statusFamily = response.getStatusInfo().getFamily();

        if (statusFamily == Response.Status.Family.SUCCESSFUL) {
            String userId = CreatedResponseUtil.getCreatedId(response);
            try {
                log.info("Created user with id {}", userId);
                assignUserRole(userId, dto.getRole());
                assignUserPassword(userId, dto.getPassword());
            } catch (Exception e) {
                usersResource.delete(user.getId()); // revert changes
                throw new RuntimeException(e);
            }
        } else {
            throw new KeycloakUserCreationException("Unable to create keycloak user: " + response.getStatusInfo().getReasonPhrase());
        }
    }

    public void validateUser(String email) {
        log.info("Validating user with email {}", email);
        RealmResource realmResource = keycloakClient.realm(realm);
        List<UserRepresentation> userList = realmResource.users().searchByEmail(email, true);

        if (userList.isEmpty()) {
            throw new KeycloakUserNotFoundException("Did not find user with email " + email);
        }
        UserRepresentation user = userList.getFirst();
        user.setEnabled(true);

        realmResource.users().get(user.getId()).update(user);
    }

    private void assignUserPassword(String id, String password) {
        log.info("Assigning user password for user with id {}", id);
        try {
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(password);
            credential.setTemporary(false);

            RealmResource realmResource = keycloakClient.realm(realm);
            UserResource userResource = realmResource.users().get(id);
            userResource.resetPassword(credential);
        } catch (Exception e) {
            throw new KeycloakPasswordAssignmentException("Error assigning user password: " + e.getMessage());
        }
    }

    private void assignUserRole(String id, String roleName) {
        log.info("Assigning role {} to user with id {}", roleName, id);
        try {
            RealmResource realmResource = keycloakClient.realm(realm);
            RoleRepresentation role = realmResource.roles().get(roleName).toRepresentation();

            UserResource userResource = realmResource.users().get(id);
            userResource.roles().realmLevel().add(Collections.singletonList(role));
        } catch (Exception e) {
           throw new KeycloakRoleAssignmentException("Error adding role " + roleName + " to user with id " + id);
        }
    }

    private UserRepresentation createUserRepresentation(AuthUserRequestDto dto) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(dto.getEmail());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEnabled(false);
        return user;
    }
}
