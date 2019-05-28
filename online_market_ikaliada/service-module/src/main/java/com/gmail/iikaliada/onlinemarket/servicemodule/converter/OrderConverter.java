package com.gmail.iikaliada.onlinemarket.servicemodule.converter;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Order;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.OrderId;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.OrderDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.OrderDTOId;

public interface OrderConverter {
    OrderDTO toOrderDTO(Order order);

    OrderDTOId toOrderDTOId(OrderId orderId);
}
