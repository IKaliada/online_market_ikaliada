package com.gmail.iikaliada.onlinemarket.servicemodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.OrderRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.OrderStatusRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Order;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.OrderStatus;
import com.gmail.iikaliada.onlinemarket.servicemodule.OrderStatusService;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.OrderStatusConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.OrderStatusDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;
    private final OrderStatusConverter orderStatusConverter;
    private final OrderRepository orderRepository;

    public OrderStatusServiceImpl(OrderStatusRepository orderStatusRepository,
                                  OrderStatusConverter orderStatusConverter,
                                  OrderRepository orderRepository) {
        this.orderStatusRepository = orderStatusRepository;
        this.orderStatusConverter = orderStatusConverter;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public List<OrderStatusDTO> getOrderStatus() {
        List<OrderStatus> statuses = orderStatusRepository.getAllEntity();
        return statuses.stream()
                .map(orderStatusConverter::toOrderStatusDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateStatus(String uId, Long id) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(id);
        Order order = orderRepository.getOrderByUniqueId(uId);
        order.setOrderStatus(orderStatus);
        orderRepository.merge(order);
    }
}
