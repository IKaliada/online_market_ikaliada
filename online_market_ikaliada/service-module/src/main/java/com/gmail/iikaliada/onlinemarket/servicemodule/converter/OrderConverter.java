package com.gmail.iikaliada.onlinemarket.servicemodule.converter;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Order;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.OrderDTO;

public interface OrderConverter {

    OrderDTO toOrderDTO(Order order);

    Order fromOrderDTO(OrderDTO orderDTO);
}
