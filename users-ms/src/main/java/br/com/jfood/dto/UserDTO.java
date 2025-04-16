package br.com.jfood.dto;

import jakarta.validation.constraints.*;

public abstract class UserDTO {

    @NotBlank(message = "Username can not be blank.")
    @Size(min = 3, max = 100, message = "The name must be between 3 and 100 characters long.")
    private String username;

    @NotBlank(message = "Email can not be blank.")
    @Email(message = "Invalid e-mail.")
    private String email;

    @NotBlank(message = "Phone can not be blank.")
    private String phone;

    @NotBlank(message = "Address can not be blank.")
    private String address;

    public UserDTO() {
    }

    public UserDTO(String username, String email, String phone, String address) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.address = address;
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
}
