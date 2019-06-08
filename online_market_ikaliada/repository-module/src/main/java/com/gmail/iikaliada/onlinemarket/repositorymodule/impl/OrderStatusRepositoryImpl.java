package com.gmail.iikaliada.onlinemarket.repositorymodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.OrderStatusRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.OrderStatus;
import org.springframework.stereotype.Repository;

@Repository
public class OrderStatusRepositoryImpl extends GenericRepositoryImpl<Long, OrderStatus> implements OrderStatusRepository {
}
