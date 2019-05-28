package com.gmail.iikaliada.onlinemarket.servicemodule.converter.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Order;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.OrderId;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.ItemConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.OrderConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.UserConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.OrderDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.OrderDTOId;
import org.springframework.stereotype.Component;

@Component
public class OrderConverterImpl implements OrderConverter {

    private final ItemConverter itemConverter;
    private final UserConverter userConverterl;

    public OrderConverterImpl(ItemConverter itemConverter, UserConverter userConverterl) {
        this.itemConverter = itemConverter;
        this.userConverterl = userConverterl;
    }

    @Override
    public OrderDTO toOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUniqueId(order.getUid());
        orderDTO.setId(toOrderDTOId(order.getId()));
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setQuantity(order.getQuantity());
        orderDTO.setUserForOrderDTO(userConverterl.toUserForOrderDTO(order.getUser()));
        orderDTO.setItemDTO(itemConverter.toItemDTO(order.getItem()));
        return orderDTO;
    }

    @Override
    public OrderDTOId toOrderDTOId(OrderId orderId) {
        OrderDTOId orderDTOId = new OrderDTOId();
        orderDTOId.setItemId(orderId.getItemId());
        orderDTOId.setUserId(orderId.getUserId());
        return orderDTOId;
    }
}
