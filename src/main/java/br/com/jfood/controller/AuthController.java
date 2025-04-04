package br.com.jfood.controller;

import br.com.jfood.dto.UserDTO;
import br.com.jfood.model.User;
import br.com.jfood.service.KeycloakService;
import br.com.jfood.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final KeycloakService keycloakService;
    private final UserService userService;

    public AuthController(KeycloakService keycloakService, UserService userService) {
        this.keycloakService = keycloakService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {

        // Save user's credentials in Keycloak database.
        String keycloakId = keycloakService.createUserInKeycloak(userDTO.username(), userDTO.email(), userDTO.password());

        // Save user's additional infos in the application database.
        User user = new User();
        user.setKeycloakId(keycloakId);
        user.setUsername(userDTO.username());
        user.setEmail(userDTO.email());
        user.setName(userDTO.name());
        user.setPhone(userDTO.phone());
        user.setAddress(userDTO.address());

        userService.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}

