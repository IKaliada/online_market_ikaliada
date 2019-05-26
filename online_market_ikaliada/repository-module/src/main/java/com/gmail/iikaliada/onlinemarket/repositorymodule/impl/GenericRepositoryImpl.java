package com.gmail.iikaliada.onlinemarket.repositorymodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.GenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GenericRepositoryImpl<I, T> implements GenericRepository<I, T> {

    protected Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public GenericRepositoryImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass()
                .getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[1];
    }

    @Override
    public void persist(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public T merge(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void remove(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public T findById(I id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public void removeById(I id) {
        String deleteQuery = "delete " + entityClass.getName() + " where id = :id";
        Query q = entityManager.createQuery(deleteQuery).setParameter("id", id);
        q.executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll(int offset, int limit) {
        String query = "from " + entityClass.getName() + " c";
        Query q = entityManager.createQuery(query)
                .setFirstResult(offset)
                .setMaxResults(limit);
        return q.getResultList();
    }

    @Override
    public int getCountOfEntities() {
        String query = "SELECT COUNT(*) FROM " + entityClass.getName() + " c";
        Query q = entityManager.createQuery(query);
        return ((Number) q.getSingleResult()).intValue();
    }
}
