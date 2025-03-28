package br.com.jfood.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenericService<T>{
    List<T> findAll();
    Optional<T> findById(UUID id);
    void save(T entity);
    void delete(UUID id);
}
