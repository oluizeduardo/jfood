package br.com.jfood.service;

import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class KeycloakService {

    private final Keycloak keycloak;

    public KeycloakService(@Value("${keycloak.server-url}") String serverUrl,
                           @Value("${keycloak.realm}") String realm,
                           @Value("${keycloak.client-id}") String clientId,
                           @Value("${keycloak.client-secret}") String clientSecret,
                           @Value("${keycloak.admin-username}") String adminUsername,
                           @Value("${keycloak.admin-password}") String adminPassword) {
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm("demo")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(adminUsername)
                .password(adminPassword)
                .build();
    }

    public String createUserInKeycloak(String username, String email, String password) {
        // 1. Criação do usuário
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setEnabled(true);
        user.setEmailVerified(true);

        // 2. Definição da senha
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);
        user.setCredentials(Collections.singletonList(credential));

        // 3. Envio para o Keycloak
        try (Response response = keycloak.realm("Demo").users().create(user)) {

            if (response.getStatus() == 201) {
                // Usuário criado com sucesso, extrai o ID do Location
                String location = response.getLocation().toString();
                return location.substring(location.lastIndexOf("/") + 1);
            } else {
                // Tratamento de erro com status retornado
                throw new RuntimeException("Erro ao criar usuário no Keycloak: HTTP " + response.getStatus());
            }

        } catch (Exception e) {
            // Log de erro (idealmente usar um logger no lugar de System.out)
            throw new RuntimeException("Erro ao criar usuário no Keycloak: " + e.getMessage(), e);
        }
    }

}
