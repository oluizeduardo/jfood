package br.com.jfood.model;

public class UserEvent {

    private final String keycloakUserId;
    private final String username;

    public UserEvent(String keycloakUserId, String username) {
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
