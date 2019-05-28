package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.OrderService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderApiController {

    private OrderService orderService;

    @Autowired
    public OrderApiController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public List<OrderDTO> getArticles(@RequestParam(value = "page", defaultValue = "1") Integer pageSize) {
        return orderService.getOrders(pageSize);
    }

    @GetMapping("/orders/{uid}")
    public OrderDTO getArticles(@PathVariable("uid") Long uid) {
        return orderService.getOrderById(uid);
    }
}
