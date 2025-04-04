package br.com.jfood.dto;

import jakarta.validation.constraints.*;

public record UserDTO(
        @NotBlank(message = "Username can not be blank.")
        @Size(min = 3, max = 100, message = "The name must be between 3 and 100 characters long.")
        String username,

        @NotBlank(message = "Email can not be blank.")
        @Email(message = "Invalid e-mail.")
        String email,

        @NotBlank(message = "Password can not be blank.")
        @Size(min = 3, max = 8, message = "The password must be between 3 and 8 characters long.")
        String password,

        @NotBlank(message = "Name can not be blank.")
        @Size(min = 3, max = 100, message = "The name must be between 3 and 100 characters long.")
        String name,

        @NotBlank(message = "Phone can not be blank.")
        String phone,

        @NotBlank(message = "Address can not be blank.")
        String address
) {
}
