package com.gmail.iikaliada.onlinemarket.servicemodule;

import com.gmail.iikaliada.onlinemarket.servicemodule.model.OrderDTO;

import java.util.List;

public interface OrderService {
    List<OrderDTO> getOrders(int pageSize);

    OrderDTO getOrderById(Long uid);
}
