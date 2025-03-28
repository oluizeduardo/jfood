package br.com.jfood.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenericRepository<T>{
    List<T> findAll();
    Optional<T> findById(UUID id);
    void save(T obj);
    void delete(UUID id);
}
