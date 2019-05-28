package com.gmail.iikaliada.onlinemarket.servicemodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.OrderRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Order;
import com.gmail.iikaliada.onlinemarket.servicemodule.OrderService;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.OrderConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.iikaliada.onlinemarket.repositorymodule.constant.LimitConstants.LIMIT;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderConverter orderConverter) {
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
    }

    @Override
    @Transactional
    public List<OrderDTO> getOrders(int currentPage) {
        int offset = ((LIMIT * currentPage));
        currentPage = LIMIT * (currentPage - 1);
        List<Order> orders = orderRepository.findAll(currentPage, offset);
        return orders.stream()
                .map(orderConverter::toOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDTO getOrderById(Long uid) {
        Order order = orderRepository.getOrderByUniqueId(uid);
        return orderConverter.toOrderDTO(order);
    }
}
