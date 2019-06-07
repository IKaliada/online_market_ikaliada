package com.gmail.iikaliada.onlinemarket.servicemodule.converter;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.OrderStatus;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.OrderStatusDTO;

public interface OrderStatusConverter {

    OrderStatusDTO toOrderStatusDTO(OrderStatus orderStatus);
}
