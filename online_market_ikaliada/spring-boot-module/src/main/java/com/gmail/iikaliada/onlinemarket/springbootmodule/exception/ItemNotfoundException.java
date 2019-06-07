package com.gmail.iikaliada.onlinemarket.springbootmodule.exception;

public class ItemNotfoundException extends RuntimeException {

    public ItemNotfoundException(Long id) {
        super("Item id not found: " + id);
    }
}
