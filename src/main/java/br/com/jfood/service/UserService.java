package br.com.jfood.service;

import br.com.jfood.dto.UserDTO;
import br.com.jfood.mapper.UserMapper;
import br.com.jfood.model.User;
import br.com.jfood.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public Page<UserDTO> findAll(Pageable pageable) {
        Page<User> pageableUsers = userRepository.findAll(pageable);
        return pageableUsers.map(userMapper::toDTO);
    }

    public UserDTO findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return userMapper.toDTO(user);
        }
        return null;
    }

    public void save(UserDTO userDTO, String keycloakId) {
        User user = createUser(userDTO, keycloakId);
        userRepository.save(user);
    }

    private User createUser(UserDTO userDTO, String keycloakId) {
        User user = new User();
        user.setKeycloakId(keycloakId);
        user.setUsername(userDTO.username());
        user.setEmail(userDTO.email());
        user.setName(adjustName(userDTO.firstName(), userDTO.familyName()));
        user.setPhone(userDTO.phone());
        user.setAddress(userDTO.address());
        return user;
    }

    private String adjustName(String firstname, String familyName) {
        return firstname + " " + familyName;
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
