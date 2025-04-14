package br.com.jfood.service;

import br.com.jfood.amqp.UserAMQPConfiguration;
import br.com.jfood.dto.KeycloakUserDTO;
import br.com.jfood.dto.PageResponseDTO;
import br.com.jfood.dto.Role;
import br.com.jfood.dto.UserDTO;
import br.com.jfood.mapper.UserMapper;
import br.com.jfood.model.User;
import br.com.jfood.model.UserEvent;
import br.com.jfood.model.UserEventType;
import br.com.jfood.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final KeycloakService keycloakService;
    private final UserEventPublisherService userEventPublisher;

    public UserService(UserMapper userMapper, UserRepository userRepository,
                       KeycloakService keycloakService, UserEventPublisherService userEventPublisher) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.keycloakService = keycloakService;
        this.userEventPublisher = userEventPublisher;
    }

    @Transactional(readOnly = true)
    public PageResponseDTO<UserDTO> findAll(Pageable pageable) {
        logger.info("Finding all users.");
        Page<User> pageableUsers = userRepository.findAll(pageable);
        Page<UserDTO> dtoPage = pageableUsers.map(userMapper::toDTO);
        return new PageResponseDTO<UserDTO>(dtoPage);
    }

    public UserDTO findUserById(Long id) {
        Optional<User> optionalUser = findById(id);
        return optionalUser.map(userMapper::toDTO).orElse(null);
    }

    @Transactional(readOnly = true)
    private Optional<User> findById(Long id) {
        logger.info("Finding user by id [{}].", id);
        return userRepository.findById(id);
    }

    public void save(KeycloakUserDTO keycloakUserDTO) {
        String keycloakUserId = null;
        try {
            keycloakUserId = saveUserInKeycloak(keycloakUserDTO);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        if (keycloakUserId == null || keycloakUserId.isBlank())
            throw new RuntimeException("Could not create Keycloak user, returned keycloakUserId null");

        saveUserInTheApplicationDatabase(keycloakUserDTO, keycloakUserId);
        publishMessageToQueue(keycloakUserId, keycloakUserDTO.getUsername(), UserEventType.CREATED);
    }

    private void publishMessageToQueue(String keycloakUserId, String username, UserEventType eventType) {
        UserEvent event = new UserEvent(keycloakUserId, username);
        if (eventType == UserEventType.CREATED) {
            userEventPublisher.publishUserCreatedEvent(event);
        } else if (eventType == UserEventType.DELETED) {
            userEventPublisher.publishUserDeletedEvent(event);
        }
    }

    private String saveUserInKeycloak(KeycloakUserDTO keycloakUserDTO) {
        logger.info("Saving user in Keycloak.");
        return keycloakService.createUserInKeycloak(keycloakUserDTO);
    }

    @Transactional
    private void saveUserInTheApplicationDatabase(KeycloakUserDTO keycloakUserDTO, String keycloakId) {
        logger.info("Saving user in the application database.");
        userRepository.save(createUser(keycloakUserDTO, keycloakId));
    }

    public void delete(UserDTO userDTO) {
        var keycloakUserId = userDTO.getKeycloakId();
        var idUser = userDTO.getId();
        if (keycloakUserId != null && idUser != null) {
            try {
                deleteKeycloakUser(keycloakUserId);
                deleteApplicationUser(idUser);
                publishMessageToQueue(keycloakUserId, userDTO.getUsername(), UserEventType.DELETED);
            } catch (Exception e) {
                logger.warn("Error deleting user. Details: {}", e.getMessage());
            }
        }
    }

    public void deleteKeycloakUser(String keycloakUserId) {
        logger.info("Deleting Keycloak user by keycloakUserId [{}].", keycloakUserId);
        keycloakService.deleteUserInKeycloak(keycloakUserId);
    }

    @Transactional
    public void deleteApplicationUser(Long idUser) {
        logger.info("Deleting user from the application database by id [{}].", idUser);
        userRepository.deleteById(idUser);
    }

    private User createUser(KeycloakUserDTO keycloakUserDTO, String keycloakId) {
        User user = new User();
        user.setKeycloakId(keycloakId);
        user.setUsername(keycloakUserDTO.getUsername());
        user.setEmail(keycloakUserDTO.getEmail());
        user.setName(adjustName(keycloakUserDTO.getFirstName(), keycloakUserDTO.getFamilyName()));
        user.setPhone(keycloakUserDTO.getPhone());
        user.setAddress(keycloakUserDTO.getAddress());
        user.setRole(Role.fromValue(keycloakUserDTO.getRole()));
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
