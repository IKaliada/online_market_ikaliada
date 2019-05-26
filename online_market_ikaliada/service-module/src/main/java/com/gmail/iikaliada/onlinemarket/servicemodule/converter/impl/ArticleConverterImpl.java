package com.gmail.iikaliada.onlinemarket.servicemodule.converter.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Article;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.ArticleConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.CommentConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.UserConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleForNewsDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleForPageDTO;
import org.springframework.stereotype.Component;

@Component
public class ArticleConverterImpl implements ArticleConverter {

    private final UserConverter userConverter;

    private final CommentConverter commentConverter;

    public ArticleConverterImpl(UserConverter userConverter, CommentConverter commentConverter) {
        this.userConverter = userConverter;
        this.commentConverter = commentConverter;
    }

    @Override
    public ArticleDTO toArticleDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setArticle(article.getArticle());
        articleDTO.setDescription(article.getDescription());
        articleDTO.setDate(article.getDate());
        articleDTO.setUserForUiDTO(userConverter.toUserForUiDTO(article.getUser()));
        return articleDTO;
    }

    @Override
    public ArticleForPageDTO toArticleForPageDTO(Article article) {
        ArticleForPageDTO articleForPageDTO = new ArticleForPageDTO();
        articleForPageDTO.setId(article.getId());
        articleForPageDTO.setArticle(article.getArticle());
        articleForPageDTO.setDescription(article.getDescription());
        articleForPageDTO.setDate(article.getDate());
        articleForPageDTO.setUserForUiDTO(userConverter.toUserForUiDTO(article.getUser()));
        articleForPageDTO.setCommentDTOList(commentConverter.toCommentDTO(article.getComment()));
        return articleForPageDTO;
    }

    @Override
    public Article fromArticleDTO(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setId(articleDTO.getId());
        article.setArticle(articleDTO.getArticle());
        article.setDescription(articleDTO.getDescription());
        article.setDate(articleDTO.getDate());
        article.setUser(userConverter.fromUserForUiDTO(articleDTO.getUserForUiDTO()));
        return article;
    }

    @Override
    public Article fromArticleForNewsDTO(ArticleForNewsDTO articleForNewsDTO) {
        Article article = new Article();
        article.setId(articleForNewsDTO.getId());
        article.setArticle(articleForNewsDTO.getArticle());
        article.setDescription(articleForNewsDTO.getDescription());
        article.setDate(articleForNewsDTO.getDate());
        article.setUser(userConverter.fromUserForArticleDTO(articleForNewsDTO.getUserForArticleDTO()));
        return article;
    }
}
