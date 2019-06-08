package com.gmail.iikaliada.onlinemarket.springbootmodule.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String uId) {
        super("Order not found: " + uId);
    }
}
