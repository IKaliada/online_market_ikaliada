package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.OrderService;
import com.gmail.iikaliada.onlinemarket.servicemodule.OrderStatusService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.OrderDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.OrderStatusDTO;
import com.gmail.iikaliada.onlinemarket.springbootmodule.handler.PaginationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private PaginationHandler pagination;
    private final OrderService orderService;
    private final OrderStatusService orderStatusService;

    public OrderController(OrderService orderService, OrderStatusService orderStatusService) {
        this.orderService = orderService;
        this.orderStatusService = orderStatusService;
    }

    @GetMapping("/private/orders")
    public String getOrders(
            Model model,
            @RequestParam(name = "page", defaultValue = "1") Integer currentPage) {
        model.addAttribute("currentPage", currentPage);
        List<OrderDTO> orders = orderService.getOrders(currentPage);
        model.addAttribute("orders", orders);
        int totalPage = orderService.getTotalPages();
        pagination.getPagination(currentPage, model, totalPage);
        return "orders";
    }

    @PostMapping("/private/orders/{id}/add")
    public String addOrders(@Valid @RequestParam("quantity") Long quantity,
                            @PathVariable("id") Long id) {
        orderService.add(quantity, id);
        return "redirect:/private/orders/find";
    }

    @GetMapping("/private/orders/find")
    public String getOrdersByUserId(Model model,
                                    @RequestParam(name = "page", defaultValue = "1") Integer currentPage) {
        List<OrderDTO> orders = orderService.getOrdersByUsername(currentPage);
        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", currentPage);
        int totalPage = orderService.getTotalPagesByUserId();
        pagination.getPagination(currentPage, model, totalPage);
        return "orders";
    }

    @GetMapping("/private/orders/{uId}")
    public String getOrdersByUId(@PathVariable("uId") String uId, Model model) {
        OrderDTO order = orderService.getOrderById(uId);
        model.addAttribute("order", order);
        List<OrderStatusDTO> statuses = orderStatusService.getOrderStatus();
        model.addAttribute("statuses", statuses);
        return "order";
    }

    @PostMapping("/private/orders/{uId}/update")
    public String updateOrdersStatus(@PathVariable("uId") String uId, OrderDTO orderDTO) {
        orderStatusService.updateStatus(uId, orderDTO.getOrderStatusDTO().getId());
        return "redirect:/private/orders/{uId}";
    }
}
