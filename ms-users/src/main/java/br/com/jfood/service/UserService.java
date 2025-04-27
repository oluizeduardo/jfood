package br.com.jfood.service;

import br.com.jfood.dto.*;
import br.com.jfood.mapper.UserMapper;
import br.com.jfood.model.BaseResponse;
import br.com.jfood.model.User;
import br.com.jfood.model.UserEvent;
import br.com.jfood.model.UserEventType;
import br.com.jfood.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public UserService(UserMapper userMapper, UserRepository userRepository, KeycloakService keycloakService,
                       UserEventPublisherService userEventPublisher) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.keycloakService = keycloakService;
        this.userEventPublisher = userEventPublisher;
    }

    @Transactional
    public ResponseEntity<Object> add(KeycloakUserDTO keycloakUserDTO) {
        String keycloakUserId = null;
        try {
            keycloakUserId = saveUserInKeycloak(keycloakUserDTO);
        } catch (RuntimeException e) {
            logger.warn("BAD REQUEST - {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(e.getMessage()));
        }

        if (keycloakUserId == null || keycloakUserId.isBlank()) {
            String baseMessage = "Could not create Keycloak user";
            logger.warn("{}, returned invalid keycloakUserId.", baseMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse(baseMessage));
        }

        User user = createUser(keycloakUserDTO, keycloakUserId);

        saveUserInTheApplicationDatabase(user);
        publishMessageToQueue(user, UserEventType.CREATED);

        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDTO(user));
    }

    private String saveUserInKeycloak(KeycloakUserDTO keycloakUserDTO) {
        logger.info("Saving user in Keycloak.");
        return keycloakService.createUserInKeycloak(keycloakUserDTO);
    }

    @Transactional
    private void saveUserInTheApplicationDatabase(User user) {
        logger.info("Saving user in the application database.");
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public PageResponseDTO<DatabaseUserDTO> findAll(Pageable pageable) {
        logger.info("Finding all users.");
        Page<User> pageableUsers = userRepository.findAll(pageable);
        Page<DatabaseUserDTO> dtoPage = pageableUsers.map(userMapper::toDTO);
        return new PageResponseDTO<DatabaseUserDTO>(dtoPage);
    }

    @Transactional(readOnly = true)
    private Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findById(Long id) {
        if (id == null) return badRequest("User id not informed");

        logger.info("Finding user by id: [{}].", id);
        Optional<User> optionalUser = findUserById(id);
        if (optionalUser.isEmpty()) {
            logUserNotFound(id);
            return userNotFound();
        }
        return ResponseEntity.ok(optionalUser.map(userMapper::toDTO));
    }

    public ResponseEntity<Object> delete(Long id) {
        if (id == null) return badRequest("User id not informed");

        logger.info("Deleting user by id: [{}].", id);
        Optional<User> optionalUser = findUserById(id);
        if (optionalUser.isEmpty()) {
            logUserNotFound(id);
            return userNotFound();
        }

        User foundUser = optionalUser.get();
        var keycloakUserId = foundUser.getKeycloakId();
        var idUser = foundUser.getId();

        try {
            deleteKeycloakUser(keycloakUserId);
            deleteApplicationUser(idUser);
            publishMessageToQueue(foundUser, UserEventType.DELETED);
        } catch (Exception e) {
            logger.warn("Error deleting user. Details: {}", e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    private void publishMessageToQueue(User user, UserEventType eventType) {
        try {
            UserEvent event = createUserEvent(user);
            if (eventType == UserEventType.CREATED) {
                userEventPublisher.publishUserCreatedEvent(event);
            } else if (eventType == UserEventType.DELETED) {
                userEventPublisher.publishUserDeletedEvent(event);
            }
        } catch (RuntimeException e) {
            logger.warn(e.getMessage());
        }
    }

    private UserEvent createUserEvent(User user) throws RuntimeException {
        if (user == null || user.getKeycloakId().isBlank())
            throw new RuntimeException("Could not create UserEvent, received invalid user data");
        return new UserEvent(user.getKeycloakId(), user.getUsername(), user.getEmail());
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

    @Transactional
    public ResponseEntity<Object> update(Long id, KeycloakUserDTO dto) {
        if (id == null || dto == null)
            return badRequest("Invalid request");

        Optional<User> optionalUser = findUserById(id);
        if (optionalUser.isEmpty()) {
            logUserNotFound(id);
            return userNotFound();
        }

        User user = optionalUser.get();

        // Try updating in Keycloak first.
        try {
            keycloakService.updateUserInKeycloak(user.getKeycloakId(), dto);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return null;
        }

        // Update user in the application's database.
        if (dto.getFirstName() != null && dto.getFamilyName() != null)
            user.setName(adjustName(dto.getFirstName(), dto.getFamilyName()));
        if (dto.getAddress() != null)
            user.setAddress(dto.getAddress());
        if (dto.getEmail() != null)
            user.setEmail(dto.getEmail());
        if (dto.getPhone() != null)
            user.setPhone(dto.getPhone());

        return ResponseEntity.ok(user);
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

    private ResponseEntity<Object> userNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse("User not found."));
    }

    private void logUserNotFound(Long id) {
        logger.info("User not found ID: [{}].", id);
    }

    private ResponseEntity<Object> badRequest(String message) {
        message = (message == null || message.isEmpty()) ? "Bad Request" : message;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(message));
    }
}
