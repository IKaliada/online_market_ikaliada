package com.gmail.iikaliada.onlinemarket.servicemodule.model;

public class OrderDTO {
    private Long uniqueId;
    private OrderDTOId id;
    private String orderStatus;
    private Long quantity;
    private ItemDTO itemDTO;
    private UserForOrderDTO userForOrderDTO;

    public Long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Long uniqueId) {
        this.uniqueId = uniqueId;
    }

    public OrderDTOId getId() {
        return id;
    }

    public void setId(OrderDTOId id) {
        this.id = id;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
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
