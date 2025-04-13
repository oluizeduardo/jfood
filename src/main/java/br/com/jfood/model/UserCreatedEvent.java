package br.com.jfood.model;

import java.io.Serializable;

public class UserCreatedEvent {

    private final String keycloakUserId;
    private final String username;

    public UserCreatedEvent(String keycloakUserId, String username) {
        this.keycloakUserId = keycloakUserId;
        this.username = username;
    }

    public String getKeycloakUserId() {
        return keycloakUserId;
    }

    public String getUsername() {
        return username;
    }
}
