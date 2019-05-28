package com.gmail.iikaliada.onlinemarket.repositorymodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.ArticleRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Article;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Comment;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository
public class ArticleRepositoryImpl extends GenericRepositoryImpl<Long, Article> implements ArticleRepository {

    @SuppressWarnings(value = "unchecked")
    public List<Article> getArticleByKeyWord(String keyWord) {
        String articleByKeyWordQuery = "from "+ entityClass.getName() +
                " WHERE description like :keyWord";
        Query query = entityManager.createQuery(articleByKeyWordQuery);
        query.setParameter("keyWord", "%" + keyWord + "%");
        return query.getResultList();
    }
}
