package com.gmail.iikaliada.onlinemarket.servicemodule.converter.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.OrderStatus;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.OrderStatusConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.OrderStatusDTO;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusConverterImpl implements OrderStatusConverter {
    @Override
    public OrderStatusDTO toOrderStatusDTO(OrderStatus orderStatus) {
        OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
        orderStatusDTO.setId(orderStatus.getId());
        orderStatusDTO.setName(orderStatus.getName());
        return orderStatusDTO;
    }
}
