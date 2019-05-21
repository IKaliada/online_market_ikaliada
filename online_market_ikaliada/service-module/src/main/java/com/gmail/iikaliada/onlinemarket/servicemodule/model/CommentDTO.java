package com.gmail.iikaliada.onlinemarket.servicemodule.model;

import java.util.Date;

public class CommentDTO {

    private Long id;
    private String content;
    private Date date;
    private UserForUiDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UserForUiDTO getUser() {
        return user;
    }

    public void setUser(UserForUiDTO user) {
        this.user = user;
    }
}
