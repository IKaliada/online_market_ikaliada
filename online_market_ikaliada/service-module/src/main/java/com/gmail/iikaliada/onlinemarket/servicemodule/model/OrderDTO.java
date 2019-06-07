package com.gmail.iikaliada.onlinemarket.servicemodule.model;

import java.math.BigDecimal;

public class OrderDTO {

    private String uId;
    private OrderStatusDTO orderStatusDTO;
    private Long quantity;
    private BigDecimal totalPrice;
    private ItemDTO itemDTO;
    private UserForOrderDTO userForOrderDTO;

    public String getUId() {
        return uId;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }

    public OrderStatusDTO getOrderStatusDTO() {
        return orderStatusDTO;
    }

    public void setOrderStatusDTO(OrderStatusDTO orderStatusDTO) {
        this.orderStatusDTO = orderStatusDTO;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ItemDTO getItemDTO() {
        return itemDTO;
    }

    public void setItemDTO(ItemDTO itemDTO) {
        this.itemDTO = itemDTO;
    }

    public UserForOrderDTO getUserForOrderDTO() {
        return userForOrderDTO;
    }

    public void setUserForOrderDTO(UserForOrderDTO userForOrderDTO) {
        this.userForOrderDTO = userForOrderDTO;
    }
}
