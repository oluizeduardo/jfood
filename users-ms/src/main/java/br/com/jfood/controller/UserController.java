package br.com.jfood.controller;

import br.com.jfood.dto.KeycloakUserDTO;
import br.com.jfood.dto.PageResponseDTO;
import br.com.jfood.dto.UserDTO;
import br.com.jfood.model.BaseResponse;
import br.com.jfood.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid KeycloakUserDTO keycloakUserDTO) {
        try {
            userService.save(keycloakUserDTO);
        } catch (Exception e) {
            logger.warn("BAD REQUEST - {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public PageResponseDTO<UserDTO> getAllUsers(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        UserDTO foundUser = userService.findUserById(id);
        if (foundUser == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse("User not found."));
        }
        return ResponseEntity.ok(foundUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        UserDTO foundUser = userService.findUserById(id);
        if (foundUser == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse("User not found."));
        }
        userService.delete(foundUser);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody KeycloakUserDTO keycloakUserDTO) {
        UserDTO updatedUser = userService.updateUser(id, keycloakUserDTO);
        if (updatedUser == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse("User not found."));
        }
        return ResponseEntity.ok(updatedUser);
    }
}

