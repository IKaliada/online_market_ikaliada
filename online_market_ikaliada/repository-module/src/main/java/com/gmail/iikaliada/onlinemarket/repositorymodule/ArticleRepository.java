package com.gmail.iikaliada.onlinemarket.repositorymodule;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Article;

import java.sql.Connection;

public interface ArticleRepository extends GenericRepository<Long, Article> {
    Article getArticleByKeyWord(Connection connection, String keyWord);
}
