package br.com.jfood.dto;

import jakarta.validation.constraints.*;

public class KeycloakUserDTO extends UserDTO {

    @NotBlank(message = "FirstName can not be blank.")
    @Size(min = 3, max = 100, message = "The FirstName must be between 3 and 100 characters long.")
    private String firstName;

    @NotBlank(message = "familyName can not be blank.")
    @Size(min = 3, max = 100, message = "The familyName must be between 3 and 100 characters long.")
    private String familyName;

    @NotBlank(message = "Password can not be blank.")
    @Size(min = 3, max = 8, message = "The password must be between 3 and 8 characters long.")
    private String password;

    @NotNull(message = "Role is required.")
    @Min(value = 1, message = "Role must be greater than zero.")
    private Integer role;

    public KeycloakUserDTO() {
    }

    public KeycloakUserDTO(String username, String email, String phone, String address,
                           String firstName, String familyName, String password, Integer role) {
        super(username, email, phone, address);
        this.firstName = firstName;
        this.familyName = familyName;
        this.password = password;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
