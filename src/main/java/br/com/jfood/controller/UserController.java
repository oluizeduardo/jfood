package br.com.jfood.controller;

import br.com.jfood.dto.UserDTO;
import br.com.jfood.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO foundUser = userService.findById(id);
        if (foundUser != null) {
            return ResponseEntity.ok(foundUser);
        }
        return ResponseEntity.notFound().build();
    }

//    @PostMapping
//    public ResponseEntity<Void> createUser(@RequestBody @Valid UserDTO userDTO) {
//        userService.save(userMapper.toUser(userDTO));
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Object> deleteUser(@PathVariable UUID id) {
//        if (userService.findById(id).isPresent()) {
//            userService.delete(id);
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(new MessageResponse("User not found."));
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Object> updateUser(@PathVariable UUID id, @RequestBody @Valid UserDTO userDTO) {
//        Optional<User> foundUser = userService.findById(id);
//
//        if (foundUser.isPresent()) {
//            User existingUser = foundUser.get();
//
//            existingUser.setName(userDTO.getName());
//            existingUser.setCpf(userDTO.getCpf());
//            existingUser.setEmail(userDTO.getEmail());
//            existingUser.setRole(Role.getFromValue(userDTO.getRole()));
//
//            return ResponseEntity.ok(existingUser);
//        } else {
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body(new MessageResponse("User not found."));
//        }
//    }

}
