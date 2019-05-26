package com.gmail.iikaliada.onlinemarket.repositorymodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.ArticleRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Article;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ArticleRepositoryImpl extends GenericRepositoryImpl<Long, Article> implements ArticleRepository {

    @Override
    @SuppressWarnings("unchecked")
    public List<Object[]> getArticles(int offset, int limit) {
        String getArticlesQuery = "select a.id, a.article, SUBSTRING(a.description, 1, 200), a.date, a.user from " +
                entityClass.getName() + " a ORDER BY a.date DESC";
        Query query = entityManager.createQuery(getArticlesQuery)
                .setFirstResult(offset)
                .setMaxResults(limit);
        return (List<Object[]>) query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Object[]> getArticleByKeyWord(String keyWord) {
        String articleByKeyWordQuery = "SELECT a.id, a.article, SUBSTRING(a.description, 1, 100), a.date, a.user_id," +
                " u.name, u.lastname, us.name as user_name, us.lastname as user_lastname, " +
                "c.id as comment_id, c.date as comment_date, c.content " +
                "FROM articles a JOIN user u ON a.user_id = u.id JOIN comment c ON a.id = c.article_id " +
                "JOIN user us ON c.user_id = us.id WHERE MATCH (description) AGAINST (?)";
        Query query = entityManager.createNativeQuery(articleByKeyWordQuery);
        query.setParameter(1, keyWord);
        return (List<Object[]>) query.getResultList();
    }
}
