package br.com.jfood.controller;

import br.com.jfood.dto.UserDTO;
import br.com.jfood.model.BaseResponse;
import br.com.jfood.service.KeycloakService;
import br.com.jfood.service.UserService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Object> registerUser(@RequestBody UserDTO userDTO) {
        try {
            // Save user in Keycloak database.
            String keycloakId = keycloakService.createUserInKeycloak(userDTO);
            // Save additional user's data in the application's database.
            userService.save(userDTO, keycloakId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

