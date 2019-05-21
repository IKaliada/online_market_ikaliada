package com.gmail.iikaliada.onlinemarket.servicemodule.model;

import java.util.Date;

public class ReviewDTO {

    private Long id;
    private String text;
    private Date date;
    private Boolean shown;
    private UserForUiDTO userForUiDTO;

    public UserForUiDTO getUserForUiDTO() {
        return userForUiDTO;
    }

    public void setUserForUiDTO(UserForUiDTO userForUiDTO) {
        this.userForUiDTO = userForUiDTO;
    }

    public Boolean getShown() {
        return shown;
    }

    public void setShown(Boolean shown) {
        this.shown = shown;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
