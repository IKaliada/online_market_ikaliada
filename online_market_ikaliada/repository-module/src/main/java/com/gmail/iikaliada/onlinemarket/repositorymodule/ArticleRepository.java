package com.gmail.iikaliada.onlinemarket.repositorymodule;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Article;

import java.util.List;

public interface ArticleRepository extends GenericRepository<Long, Article> {

    List<Object[]> getArticleByKeyWord(String keyWord);

    List<Object[]> getArticles(int offset, int limit);
}
