package br.com.jfood.repository;

import br.com.jfood.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository implements GenericRepository<User> {

    private final List<User> users = new ArrayList<>();

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    @Override
    public void save(User user) {
        user.setId(UUID.randomUUID());
        users.add(user);
    }

    @Override
    public void delete(UUID id) {
        users.removeIf(user -> user.getId().equals(id));
    }
}