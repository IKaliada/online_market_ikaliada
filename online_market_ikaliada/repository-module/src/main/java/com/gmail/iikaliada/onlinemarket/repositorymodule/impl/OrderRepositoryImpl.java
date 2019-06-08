package com.gmail.iikaliada.onlinemarket.repositorymodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.OrderRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class OrderRepositoryImpl extends GenericRepositoryImpl<Long, Order> implements OrderRepository {

    @Override
    public Order getOrderByUniqueId(String uId) {
        String orderByIdQuery = "from " + entityClass.getName() + " o where o.uid =:uId";
        Query query = entityManager.createQuery(orderByIdQuery);
        query.setParameter("uId", uId);
        return (Order) query.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Order> findByUserId(Long id, int offset, int limit) {
        String orderByUserIdQuery = "from " + entityClass.getName() + " o where o.user.id =:id";
        Query query = entityManager.createQuery(orderByUserIdQuery)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public int getCountOfPagesByUserId(Long id) {
        String query = "SELECT COUNT(*) FROM " + entityClass.getName() + " o where o.user.id =:id";
        Query q = entityManager.createQuery(query)
                .setParameter("id", id);
        return ((Number) q.getSingleResult()).intValue();
    }
}
