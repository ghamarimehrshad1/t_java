package com.example.library.repository;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface Repository<T,ID> {

    public Collection<T> findAll() throws SQLException;
    public T save(T entity) throws SQLException;
    public void delete(T entity) throws SQLException;
    public Optional<T> findById(ID id) throws SQLException;

}
