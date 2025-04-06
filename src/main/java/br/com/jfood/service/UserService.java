package br.com.jfood.service;

import br.com.jfood.dto.KeycloakUserDTO;
import br.com.jfood.dto.UserDTO;
import br.com.jfood.mapper.UserMapper;
import br.com.jfood.model.User;
import br.com.jfood.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final KeycloakService keycloakService;

    public UserService(UserMapper userMapper, UserRepository userRepository, KeycloakService keycloakService) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.keycloakService = keycloakService;
    }

    public Page<UserDTO> findAll(Pageable pageable) {
        Page<User> pageableUsers = userRepository.findAll(pageable);
        return pageableUsers.map(userMapper::toDTO);
    }

    public UserDTO findUserById(Long id) {
        Optional<User> optionalUser = findById(id);
        return optionalUser.map(userMapper::toDTO).orElse(null);
    }

    private Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void save(KeycloakUserDTO keycloakUserDTO) {
        // Save user in Keycloak database.
        String keycloakId = keycloakService.createUserInKeycloak(keycloakUserDTO);
        // Save user's details in the application's database.
        userRepository.save(createUser(keycloakUserDTO, keycloakId));
    }

    public void delete(UserDTO userDTO) {
        var keycloakUserId = userDTO.getKeycloakId();
        var idUser = userDTO.getId();
        if (keycloakUserId != null && idUser != null) {
            try {
                keycloakService.deleteUserInKeycloak(keycloakUserId);
                userRepository.deleteById(idUser);
            } catch (Exception e) {
                System.out.println("Error deleting user. Details: " + e.getMessage());
            }
        }
    }

    private User createUser(KeycloakUserDTO keycloakUserDTO, String keycloakId) {
        User user = new User();
        user.setKeycloakId(keycloakId);
        user.setUsername(keycloakUserDTO.getUsername());
        user.setEmail(keycloakUserDTO.getEmail());
        user.setName(adjustName(keycloakUserDTO.getFirstName(), keycloakUserDTO.getFamilyName()));
        user.setPhone(keycloakUserDTO.getPhone());
        user.setAddress(keycloakUserDTO.getAddress());
        return user;
    }

    private String adjustName(String firstname, String familyName) {
        return firstname + " " + familyName;
    }

    @Transactional
    public UserDTO updateUser(Long id, KeycloakUserDTO keycloakUserDTO) {
        Optional<User> optionalUser = findById(id);
        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

        // Try updating in Keycloak first.
        try {
            keycloakService.updateUserInKeycloak(user.getKeycloakId(), keycloakUserDTO);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return null;
        }

        // Update user in the application's database.
        user.setName(adjustName(keycloakUserDTO.getFirstName(), keycloakUserDTO.getFamilyName()));
        user.setAddress(keycloakUserDTO.getAddress());
        user.setEmail(keycloakUserDTO.getEmail());
        user.setPhone(keycloakUserDTO.getPhone());

        return userMapper.toDTO(user);
    }
}
