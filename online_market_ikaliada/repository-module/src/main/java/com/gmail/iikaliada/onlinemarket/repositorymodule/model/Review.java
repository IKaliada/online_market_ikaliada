package com.gmail.iikaliada.onlinemarket.repositorymodule.model;

import java.util.Date;

public class Review {

    private Long id;
    private String text;
    private Date date;
    private boolean isShown;
    private UserForReview userForReview;

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

    public boolean isShown() {
        return isShown;
    }

    public void setShown(boolean shown) {
        isShown = shown;
    }

    public UserForReview getUserForReview() {
        return userForReview;
    }

    public void setUserForReview(UserForReview userForReview) {
        this.userForReview = userForReview;
    }
}
