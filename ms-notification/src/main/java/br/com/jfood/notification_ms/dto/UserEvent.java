package br.com.jfood.notification_ms.dto;

public class UserEvent {

    private String keycloakUserId;
    private String firstName;
    private String familyName;
    private String username;
    private String email;

    public UserEvent() {
    }

    public UserEvent(String keycloakUserId, String firstName, String familyName, String username, String email) {
        this.keycloakUserId = keycloakUserId;
        this.firstName = firstName;
        this.familyName = familyName;
        this.username = username;
        this.email = email;
    }

    public String getKeycloakUserId() {
        return keycloakUserId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
