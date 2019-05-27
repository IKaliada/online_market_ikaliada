package com.gmail.iikaliada.onlinemarket.repositorymodule;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Article;

import java.util.List;

public interface ArticleRepository extends GenericRepository<Long, Article> {

    List<Article> getArticleByKeyWord(String keyWord);

    List<Article> getArticles(int offset, int limit);
}
