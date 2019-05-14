package com.gmail.iikaliada.onlinemarket.servicemodule;

public interface MailSender {
    void send(String emailTo, String subject, String message);
}
