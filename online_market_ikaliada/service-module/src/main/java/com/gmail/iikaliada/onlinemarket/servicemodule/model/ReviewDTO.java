package com.gmail.iikaliada.onlinemarket.servicemodule.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

public class ReviewDTO {

    private Long id;
    @NotNull
    @Size(min = 10, max = 200, message = "{validation.size.message}")
    @Pattern(regexp = "^[a-zA-Z0-9/.,;:\"'?! ]+$", message = "{letter.not.correct}")
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
