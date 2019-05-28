package com.gmail.iikaliada.onlinemarket.repositorymodule;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Order;

public interface OrderRepository extends GenericRepository<Long, Order> {
    Order getOrderByUniqueId(Long uniqueId);
}
