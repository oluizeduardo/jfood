package br.com.jfood.mapper;

import br.com.jfood.dto.DatabaseUserDTO;
import br.com.jfood.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public User toUser(DatabaseUserDTO databaseUserDTO) {
        return modelMapper.map(databaseUserDTO, User.class);
    }

    public DatabaseUserDTO toDTO(User user) {
        return modelMapper.map(user, DatabaseUserDTO.class);
    }
}
