package com.gmail.iikaliada.onlinemarket.servicemodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.OrderRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Item;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Order;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.OrderStatus;
import com.gmail.iikaliada.onlinemarket.servicemodule.OrderService;
import com.gmail.iikaliada.onlinemarket.servicemodule.UserService;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.OrderConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.UserConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.AuthenticatedUserDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.OrderDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserForOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.gmail.iikaliada.onlinemarket.repositorymodule.constant.LimitConstants.LIMIT;

@Service
public class OrderServiceImpl implements OrderService {

    private final static Long DEFAULT_ORDER_STATUS = 1L;

    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final UserService userService;
    private final UserConverter userConverter;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderConverter orderConverter,
                            UserService userService,
                            UserConverter userConverter) {
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @Override
    @Transactional
    public List<OrderDTO> getOrders(int currentPage) {
        int offset = ((LIMIT * currentPage));
        currentPage = LIMIT * (currentPage - 1);
        List<Order> orders = orderRepository.findAll(currentPage, offset);
        return orders.stream()
                .map(orderConverter::toOrderDTO)
                .peek(orderDTO -> orderDTO.setTotalPrice(BigDecimal.valueOf(orderDTO.getQuantity())
                        .multiply(orderDTO.getItemDTO().getPrice())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDTO getOrderById(String uId) {
        Order order = orderRepository.getOrderByUniqueId(uId);
        OrderDTO orderDTO = orderConverter.toOrderDTO(order);
        orderDTO.setTotalPrice(BigDecimal.valueOf(orderDTO.getQuantity())
                .multiply(orderDTO.getItemDTO().getPrice()));
        return orderDTO;
    }

    @Override
    @Transactional
    public void add(Long quantity, Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        AuthenticatedUserDTO authenticatedUserDTO = userService.getAuthenticatedUser(currentPrincipalName);
        OrderDTO orderDTO = new OrderDTO();
        UserForOrderDTO userForOrderDTO = new UserForOrderDTO();
        userForOrderDTO.setId(authenticatedUserDTO.getId());
        orderDTO.setUserForOrderDTO(userForOrderDTO);
        orderDTO.setQuantity(quantity);
        Order order = orderConverter.fromOrderDTO(orderDTO);
        order.setDate(new Date());
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(DEFAULT_ORDER_STATUS);
        order.setOrderStatus(orderStatus);
        Item item = new Item();
        item.setId(id);
        order.setItem(item);
        order.setUid(UUID.randomUUID().toString());
        orderRepository.persist(order);
    }

    @Override
    @Transactional
    public int getTotalPages() {
        int totalEntities = orderRepository.getCountOfEntities();
        return getPagesNumber(totalEntities);
    }

    @Override
    @Transactional
    public int getTotalPagesByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        AuthenticatedUserDTO authenticatedUserDTO = userService.getAuthenticatedUser(currentPrincipalName);
        int totalEntities = orderRepository.getCountOfPagesByUserId(authenticatedUserDTO.getId());
        return getPagesNumber(totalEntities);
    }

    @Override
    @Transactional
    public List<OrderDTO> getOrdersByUsername(int currentPage) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        AuthenticatedUserDTO authenticatedUserDTO = userService.getAuthenticatedUser(currentPrincipalName);
        int offset = ((LIMIT * currentPage));
        currentPage = LIMIT * (currentPage - 1);
        List<Order> orders = orderRepository.findByUserId(authenticatedUserDTO.getId(), currentPage, offset);
        return orders.stream()
                .map(orderConverter::toOrderDTO)
                .peek(orderDTO -> orderDTO.setTotalPrice(BigDecimal.valueOf(orderDTO.getQuantity())
                        .multiply(orderDTO.getItemDTO().getPrice())))
                .collect(Collectors.toList());
    }

    private int getPagesNumber(int totalEntities) {
        int pagesNumber = totalEntities / LIMIT;
        if (totalEntities % LIMIT > 0) {
            pagesNumber += 1;
        }
        return pagesNumber;
    }
}
