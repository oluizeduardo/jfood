package br.com.jfood.mapper;

import br.com.jfood.dto.UserDTO;
import br.com.jfood.model.Role;
import br.com.jfood.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public User toUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setId(UUID.randomUUID());
        user.setRole(Role.getFromValue(userDTO.getRole()));
        return user;
    }

    public UserDTO toDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
