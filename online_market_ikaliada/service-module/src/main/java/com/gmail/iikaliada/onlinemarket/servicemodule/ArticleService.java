package com.gmail.iikaliada.onlinemarket.servicemodule;

import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleForPageDTO;

import java.util.List;

public interface ArticleService {

    List<ArticleDTO> getArticle(int currentPage);

    int getTotalPages();

    ArticleForPageDTO getArticleById(Long id);

    ArticleForPageDTO getArticleByKeyWord(String keyWord);

    void add(ArticleDTO articleDTO);

    void deleteById(Long id);
}
