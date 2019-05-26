package com.gmail.iikaliada.onlinemarket.servicemodule;

import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleForNewsDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleForPageDTO;

import java.util.List;

public interface ArticleService {

    List<ArticleDTO> getArticle(int currentPage);

    int getTotalPages();

    ArticleForPageDTO getArticleById(Long id);

    List<ArticleForPageDTO> getArticleByKeyWord(String keyWord);

    void add(ArticleDTO articleDTO);

    void deleteById(Long id);

    void deleteArticleById(List<Long> ids);

    void updateArticle(ArticleForPageDTO articleForPageDTO);

    void deleteCommentById(List<Long> collect);

    void createNewArticle(ArticleForNewsDTO articleForNewsDTO);
}
