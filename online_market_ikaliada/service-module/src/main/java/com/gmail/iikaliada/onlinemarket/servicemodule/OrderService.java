package com.gmail.iikaliada.onlinemarket.servicemodule;

import com.gmail.iikaliada.onlinemarket.servicemodule.model.OrderDTO;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface OrderService {

    List<OrderDTO> getOrders(int pageSize);

    OrderDTO getOrderById(String uId);

    void add(Long quantity, Long id);

    int getTotalPages();

    int getTotalPagesByUserId();

    List<OrderDTO> getOrdersByUsername(int currentPage);
}
