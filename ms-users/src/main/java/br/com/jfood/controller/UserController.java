package br.com.jfood.controller;

import br.com.jfood.dto.KeycloakUserDTO;
import br.com.jfood.dto.PageResponseDTO;
import br.com.jfood.dto.DatabaseUserDTO;
import br.com.jfood.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/register")
    public ResponseEntity<Object> addUser(@RequestBody @Valid KeycloakUserDTO keycloakUserDTO) {
        return userService.add(keycloakUserDTO);
    }

    @GetMapping
    public PageResponseDTO<DatabaseUserDTO> findAllUsers(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        return userService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody KeycloakUserDTO keycloakUserDTO) {
        return userService.update(id, keycloakUserDTO);
    }
}

