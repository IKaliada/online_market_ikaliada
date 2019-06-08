package com.gmail.iikaliada.onlinemarket.repositorymodule;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Order;

import java.util.List;

public interface OrderRepository extends GenericRepository<Long, Order> {

    Order getOrderByUniqueId(String uId);

    List<Order> findByUserId(Long id, int offset, int limit);

    int getCountOfPagesByUserId(Long id);
}
