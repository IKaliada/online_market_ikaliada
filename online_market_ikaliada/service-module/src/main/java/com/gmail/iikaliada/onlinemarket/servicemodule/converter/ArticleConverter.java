package com.gmail.iikaliada.onlinemarket.servicemodule.converter;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Article;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleForPageDTO;

public interface ArticleConverter {

    ArticleDTO toArticleDTO(Article article);

    ArticleForPageDTO toArticleForPageDTO(Article article);

    Article fromArticleDTO(ArticleDTO articleDTO);
}
