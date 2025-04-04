package br.com.jfood.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

//    @Autowired
//    private UserMapper userMapper;
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping
//    public ResponseEntity<List<User>> getAllUsers() {
//        List<User> users = userService.findAll();
//        return ResponseEntity.ok(users);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
//        return userService.findById(id)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }

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
