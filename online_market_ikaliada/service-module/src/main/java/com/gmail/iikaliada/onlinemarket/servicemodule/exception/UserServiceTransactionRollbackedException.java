package com.gmail.iikaliada.onlinemarket.servicemodule.exception;

public class UserServiceTransactionRollbackedException extends RuntimeException {
    public UserServiceTransactionRollbackedException(String message) {
        super(message);
    }
}
