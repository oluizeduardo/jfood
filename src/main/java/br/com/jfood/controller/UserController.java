package br.com.jfood.controller;

import br.com.jfood.dto.KeycloakUserDTO;
import br.com.jfood.dto.UserDTO;
import br.com.jfood.model.BaseResponse;
import br.com.jfood.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid KeycloakUserDTO keycloakUserDTO) {
        try {
            userService.save(keycloakUserDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public Page<UserDTO> getAllUsers(Pageable pageable) {
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

