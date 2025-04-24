package br.com.jfood.dto;

public class DatabaseUserDTO extends UserDTO {

    private Long id;
    private String name;
    private String keycloakId;
    private String role;

    public DatabaseUserDTO() {
    }

    public DatabaseUserDTO(String username, String email, String phone, String address,
                           Long id, String keycloakId, String name, String role) {
        super(username, email, phone, address);
        this.id = id;
        this.keycloakId = keycloakId;
        this.name = name;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeycloakId() {
        return keycloakId;
    }

    public void setKeycloakId(String keycloakId) {
        this.keycloakId = keycloakId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
