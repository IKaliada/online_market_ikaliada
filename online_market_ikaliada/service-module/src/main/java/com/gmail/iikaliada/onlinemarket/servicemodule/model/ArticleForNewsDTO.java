package com.gmail.iikaliada.onlinemarket.servicemodule.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

public class ArticleForNewsDTO {

    private Long id;
    @NotNull
    @Size(min = 10, max = 50, message = "{validation.size.message}")
    @Pattern(regexp = "^[a-zA-Z0-9/.,;:\"'?! ]+$", message = "{letter.not.correct}")
    private String article;
    @NotNull
    @Size(min = 10, max = 1000, message = "{validation.size.message}")
    @Pattern(regexp = "^[a-zA-Z0-9/.,;:\"'?! ]+$", message = "{letter.not.correct}")
    private String description;
    private Date date;
    private AuthenticatedUserDTO authenticatedUserDTO;

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

    public AuthenticatedUserDTO getAuthenticatedUserDTO() {
        return authenticatedUserDTO;
    }

    public void setAuthenticatedUserDTO(AuthenticatedUserDTO authenticatedUserDTO) {
        this.authenticatedUserDTO = authenticatedUserDTO;
    }
}
