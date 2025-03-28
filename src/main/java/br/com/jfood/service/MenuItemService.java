package br.com.jfood.service;

import br.com.jfood.model.MenuItem;
import br.com.jfood.model.Role;
import br.com.jfood.model.User;
import br.com.jfood.repository.MenuItemRepository;
import br.com.jfood.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MenuItemService implements GenericService<MenuItem> {

    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<MenuItem> findAll() {
        return menuItemRepository.findAll();
    }

    @Override
    public Optional<MenuItem> findById(UUID id) {
        return menuItemRepository.findById(id);
    }

    @Override
    public void save(MenuItem menuItem) {
        menuItemRepository.save(menuItem);
    }

    @Override
    public void delete(UUID id) {
        menuItemRepository.delete(id);
    }

    public void save(UUID userId, MenuItem menuItem) throws RuntimeException {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent() && user.get().getRole() == Role.ADMIN) {
            save(menuItem);
        } else {
            throw new RuntimeException("Only ADMIN users can save menu items.");
        }
    }
}
