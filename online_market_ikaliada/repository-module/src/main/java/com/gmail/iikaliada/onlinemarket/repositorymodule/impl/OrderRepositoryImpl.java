package com.gmail.iikaliada.onlinemarket.repositorymodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.OrderRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Order;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class OrderRepositoryImpl extends GenericRepositoryImpl<Long, Order> implements OrderRepository {

    @Override
    public Order getOrderByUniqueId(Long uid) {
        String userByUsernameQuery = "from " + entityClass.getName() + " o where o.uid =:uid";
        Query query = entityManager.createQuery(userByUsernameQuery);
        query.setParameter("uid", uid);
        return (Order) query.getSingleResult();
    }
}
