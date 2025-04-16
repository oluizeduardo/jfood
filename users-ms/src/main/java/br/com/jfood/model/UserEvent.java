package br.com.jfood.model;

public class UserEvent {

    private final String keycloakUserId;
    private final String username;
    private final String email;

    public UserEvent(String keycloakUserId, String username, String email) {
        this.keycloakUserId = keycloakUserId;
        this.username = username;
        this.email = email;
    }

    public String getKeycloakUserId() {
        return keycloakUserId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
