package br.com.jfood.mapper;

import br.com.jfood.dto.MenuItemDTO;
import br.com.jfood.model.MenuItem;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MenuItemMapper {

    @Autowired
    private ModelMapper modelMapper;

    public MenuItem toMenuItem(MenuItemDTO menuItemDTO) {
        MenuItem menuItem = modelMapper.map(menuItemDTO, MenuItem.class);
        menuItem.setId(UUID.randomUUID());
        return menuItem;
    }

    public MenuItemDTO toDTO(MenuItem menuItem) {
        return modelMapper.map(menuItem, MenuItemDTO.class);
    }
}
