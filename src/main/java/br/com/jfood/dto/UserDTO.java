package br.com.jfood.dto;

import jakarta.validation.constraints.*;

public class UserDTO {
    @NotBlank(message = "The name can not be blank.")
    @Size(min = 3, max = 100, message = "The name must be between 3 and 100 characters long.")
    private String name;

    @NotBlank(message = "The CPF cannot be blank.")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "The CPF must be in the format 111.111.111-11.")
    private String cpf;

    @NotBlank(message = "The email can not be blank.")
    @Email(message = "Invalid e-mail.")
    private String email;

    @Min(value = 1, message = "Role must be at least 1.")
    @Max(value = 5, message = "The role cannot be greater than 2.")
    private int role;

    public UserDTO(String name, String cpf, String email, int role) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public int getRole() {
        return role;
    }
}
