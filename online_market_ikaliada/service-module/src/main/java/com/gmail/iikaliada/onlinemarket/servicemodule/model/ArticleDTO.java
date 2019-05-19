package com.gmail.iikaliada.onlinemarket.servicemodule.model;

import java.util.Date;

public class ArticleDTO {

    private Long id;
    private String article;
    private String description;
    private Date date;
    private UserForUiDTO userForUiDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UserForUiDTO getUserForUiDTO() {
        return userForUiDTO;
    }

    public void setUserForUiDTO(UserForUiDTO userForUiDTO) {
        this.userForUiDTO = userForUiDTO;
    }
}
