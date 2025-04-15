package br.com.jfood.dto;

import jakarta.validation.constraints.*;

public class KeycloakUserDTO {

    @NotBlank(message = "Username can not be blank.")
    @Size(min = 3, max = 100, message = "The name must be between 3 and 100 characters long.")
    private String username;

    @NotBlank(message = "Email can not be blank.")
    @Email(message = "Invalid e-mail.")
    private String email;

    @NotBlank(message = "Password can not be blank.")
    @Size(min = 3, max = 8, message = "The password must be between 3 and 8 characters long.")
    private String password;

    @NotBlank(message = "FirstName can not be blank.")
    @Size(min = 3, max = 100, message = "The FirstName must be between 3 and 100 characters long.")
    private String firstName;

    @NotBlank(message = "familyName can not be blank.")
    @Size(min = 3, max = 100, message = "The familyName must be between 3 and 100 characters long.")
    private String familyName;

    @NotBlank(message = "Phone can not be blank.")
    private String phone;

    @NotBlank(message = "Address can not be blank.")
    private String address;

    @NotNull(message = "Role is required.")
    @Min(value = 1, message = "Role must be greater than zero.")
    private Integer role;

    public KeycloakUserDTO() {
    }

    public KeycloakUserDTO(String username, String email, String password,
                           String firstName, String familyName,
                           String phone, String address, Integer role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.familyName = familyName;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
