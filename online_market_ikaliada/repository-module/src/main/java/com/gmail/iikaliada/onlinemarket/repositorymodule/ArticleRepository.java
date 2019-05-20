package com.gmail.iikaliada.onlinemarket.repositorymodule;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Article;

import java.sql.Connection;
import java.util.List;

public interface ArticleRepository extends GenericRepository<Long, Article> {

    Article getArticleByKeyWord(Connection connection, String keyWord);

    List<Article> getArticles(Connection connection, Integer currentPage);
}
