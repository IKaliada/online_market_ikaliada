package com.gmail.iikaliada.onlinemarket.servicemodule.model;

import com.gmail.iikaliada.onlinemarket.servicemodule.validator.LatinLetterValidator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class ArticleForNewsDTO {

    private Long id;
    @NotNull
    @Size(min = 10, max = 50, message = "{validation.size.message}")
    @LatinLetterValidator
    private String article;
    @NotNull
    @Size(min = 10, max = 1000, message = "{validation.size.message}")
    @LatinLetterValidator
    private String description;
    private Date date;
    private UserForArticleDTO userForArticleDTO;

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

    public UserForArticleDTO getUserForArticleDTO() {
        return userForArticleDTO;
    }

    public void setUserForArticleDTO(UserForArticleDTO userForArticleDTO) {
        this.userForArticleDTO = userForArticleDTO;
    }
}
