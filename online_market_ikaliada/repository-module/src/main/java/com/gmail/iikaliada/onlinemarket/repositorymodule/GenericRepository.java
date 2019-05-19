package com.gmail.iikaliada.onlinemarket.repositorymodule;

import java.sql.Connection;
import java.util.List;

public interface GenericRepository<I, T> {
    void persist(T entity);

    void merge(T entity);

    void remove(T entity);

    T findById(I id);

    void removeById(I id);

    List<T> findAll(int offset, int limit);

    int getCountOfEntities();

    Connection getConnection();
}
