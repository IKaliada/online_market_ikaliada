package com.gmail.iikaliada.onlinemarket.servicemodule;

import com.gmail.iikaliada.onlinemarket.servicemodule.model.OrderStatusDTO;

import java.util.List;

public interface OrderStatusService {

    List<OrderStatusDTO> getOrderStatus();

    void updateStatus(String uId, Long id);
}
