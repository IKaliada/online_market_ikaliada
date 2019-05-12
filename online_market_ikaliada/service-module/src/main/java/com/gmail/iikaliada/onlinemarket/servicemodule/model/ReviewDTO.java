package com.gmail.iikaliada.onlinemarket.servicemodule.model;

import java.util.Date;

public class ReviewDTO {

    private Long id;
    private String text;
    private Date date;
    private boolean isShown;
    private UserForReviewDTO userForReviewDTO;

    public ReviewDTO(Long id, String text, Date date, boolean isShown, UserForReviewDTO userForReviewDTO) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.isShown = isShown;
        this.userForReviewDTO = userForReviewDTO;
    }

    public ReviewDTO() {
    }

    public UserForReviewDTO getUserForReviewDTO() {
        return userForReviewDTO;
    }

    public void setUserForReviewDTO(UserForReviewDTO userForReviewDTO) {
        this.userForReviewDTO = userForReviewDTO;
    }

    public boolean isShown() {
        return isShown;
    }

    public void setShown(boolean shown) {
        isShown = shown;
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
