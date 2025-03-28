package br.com.jfood.repository;

import br.com.jfood.model.MenuItem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MenuItemRepository implements GenericRepository<MenuItem> {

    private final List<MenuItem> menuItems = new ArrayList<>();

    @Override
    public List<MenuItem> findAll() {
        return menuItems;
    }

    @Override
    public Optional<MenuItem> findById(UUID id) {
        return menuItems.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    @Override
    public void save(MenuItem menuItem) {
        menuItem.setId(UUID.randomUUID());
        menuItems.add(menuItem);
    }

    @Override
    public void delete(UUID id) {
        menuItems.removeIf(item -> item.getId().equals(id));
    }
}
