package com.gmail.iikaliada.onlinemarket.servicemodule.converter.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Order;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.ItemConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.OrderConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.OrderStatusConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.UserConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.OrderDTO;
import org.springframework.stereotype.Component;

@Component
public class OrderConverterImpl implements OrderConverter {

    private final ItemConverter itemConverter;
    private final UserConverter userConverter;
    private final OrderStatusConverter orderStatusConverter;

    public OrderConverterImpl(ItemConverter itemConverter,
                              UserConverter userConverter,
                              OrderStatusConverter orderStatusConverter) {
        this.itemConverter = itemConverter;
        this.userConverter = userConverter;
        this.orderStatusConverter = orderStatusConverter;
    }

    @Override
    public OrderDTO toOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUId(order.getUid());
        orderDTO.setQuantity(order.getQuantity());
        orderDTO.setOrderStatusDTO(orderStatusConverter.toOrderStatusDTO(order.getOrderStatus()));
        orderDTO.setUserForOrderDTO(userConverter.toUserForOrderDTO(order.getUser()));
        orderDTO.setItemDTO(itemConverter.toItemDTO(order.getItem()));
        return orderDTO;
    }

    @Override
    public Order fromOrderDTO(OrderDTO orderDTO) {
        Order order = new Order();
        order.setQuantity(orderDTO.getQuantity());
        order.setUser(userConverter.fromUserForOrderDTO(orderDTO.getUserForOrderDTO()));
        return order;
    }
}
