package com.gmail.iikaliada.onlinemarket.springbootmodule.exception;

public class ArticleNotFoundException extends RuntimeException {

    public ArticleNotFoundException(Long id) {
        super("Article not found: " + id);
    }
}
