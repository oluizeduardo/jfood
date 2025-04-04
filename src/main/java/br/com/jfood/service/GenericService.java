package br.com.jfood.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<T> {
    List<T> findAll();

    Optional<T> findById(Long id);

    void save(T entity);

    void delete(Long id);
}
