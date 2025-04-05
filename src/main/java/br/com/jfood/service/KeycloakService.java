package br.com.jfood.service;

import br.com.jfood.dto.UserDTO;
import jakarta.ws.rs.core.Response;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Service class responsible for interacting with Keycloak's Admin API
 * to programmatically create users.
 * <p>
 * This class uses the Keycloak Admin client to automate the user creation process,
 * avoiding the need to manually use the Keycloak admin console.
 * </p>
 * <p>
 * Example usage: Inject this service into a controller or other service
 * to register new users in your Keycloak realm.
 * <p>
 * Dependencies: Requires valid Keycloak client credentials and admin user access.
 *
 * @author luiz_costa
 */
@Service
public class KeycloakService {

    private final Keycloak keycloak;

    private final String realm;

    /**
     * Constructs the {@code KeycloakService} by initializing the Keycloak admin client
     * with credentials and configuration loaded from application properties.
     *
     * @param serverUrl     the URL of the Keycloak server (e.g., http://localhost:8080)
     * @param realm         the name of the Keycloak realm to operate in
     * @param clientId      the client ID configured in Keycloak
     * @param clientSecret  the client secret associated with the client ID
     * @param adminUsername the username of an admin user with permissions to manage users
     * @param adminPassword the password of the admin user
     */
    public KeycloakService(@Value("${keycloak.server-url}") String serverUrl,
                           @Value("${keycloak.realm}") String realm,
                           @Value("${keycloak.client-id}") String clientId,
                           @Value("${keycloak.client-secret}") String clientSecret,
                           @Value("${keycloak.admin-username}") String adminUsername,
                           @Value("${keycloak.admin-password}") String adminPassword) {

        this.realm = realm;

        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(adminUsername)
                .password(adminPassword)
                .build();
    }

    /**
     * Creates a new user in Keycloak based on the provided {@link UserDTO}.
     *
     * @param userDTO the data transfer object containing user details
     * @return the ID of the newly created user in Keycloak
     * @throws RuntimeException if the user creation fails or {@code userDTO} is null
     */
    public String createUserInKeycloak(UserDTO userDTO) {
        if (userDTO == null) {
            throw new RuntimeException("Error creating user in Keycloak: Received null UserDTO.");
        }

        UserRepresentation keycloakUser = createKeycloakUserRepresentation(userDTO);

        try (Response response = keycloak.realm(this.realm).users().create(keycloakUser)) {
            int status = response.getStatus();

            return switch (status) {
                case 201 -> {
                    String location = response.getLocation().toString();
                    yield location.substring(location.lastIndexOf("/") + 1);
                }
                case 400 -> throw new RuntimeException("Invalid user data.");
                case 409 -> throw new RuntimeException("Username or email already exists.");
                case 401, 403 ->
                        throw new RuntimeException("Unauthorized. Check admin credentials or client permissions.");
                default -> throw new RuntimeException("Unexpected error: HTTP " + status);
            };
        } catch (Exception e) {
            throw new RuntimeException("Error creating user in Keycloak: " + e.getMessage(), e);
        }
    }

    /**
     * Builds a {@link UserRepresentation} object from the provided {@link UserDTO}.
     *
     * @param userDTO the user data.
     * @return a configured {@code UserRepresentation} ready to be sent to Keycloak.
     */
    private UserRepresentation createKeycloakUserRepresentation(UserDTO userDTO) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.username());
        user.setEmail(userDTO.email());
        user.setEnabled(true);
        user.setEmailVerified(true);
        user.setFirstName(userDTO.firstName());
        user.setLastName(userDTO.familyName());

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(userDTO.password());
        credential.setTemporary(false);
        user.setCredentials(Collections.singletonList(credential));

        return user;
    }

}
