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

    @Override
    @SuppressWarnings("unchecked")
    public List<Article> getArticles(int offset, int limit) {
        String getArticlesQuery = "select a.id, a.article, SUBSTRING(a.description, 1, 200), a.date, a.user from " +
                entityClass.getName() + " a ORDER BY a.date DESC";
        Query query = entityManager.createQuery(getArticlesQuery)
                .setFirstResult(offset)
                .setMaxResults(limit);
        List resultList = query.getResultList();
        List<Article> articles = new ArrayList<>();
        for (Object object : resultList) {
            Object[] result = (Object[]) object;
            Article article = new Article();
            article.setId((Long) result[0]);
            article.setArticle((String) result[1]);
            article.setDescription((String) result[2]);
            article.setDate((Date) (result[3]));
            article.setUser((User) (result[4]));
            articles.add(article);
        }
        return articles;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Article> getArticleByKeyWord(String keyWord) {
        String articleByKeyWordQuery = "SELECT a.id, a.article, SUBSTRING(a.description, 1, 100), a.date, a.user_id," +
                " u.name, u.lastname, us.name as user_name, us.lastname as user_lastname, " +
                "c.id as comment_id, c.date as comment_date, c.content " +
                "FROM articles a JOIN user u ON a.user_id = u.id JOIN comment c ON a.id = c.article_id " +
                "JOIN user us ON c.user_id = us.id WHERE MATCH (description) AGAINST (?)";
        Query query = entityManager.createNativeQuery(articleByKeyWordQuery);
        query.setParameter(1, keyWord);
        List resultList = query.getResultList();
        List<Article> articles = new ArrayList<>();
        for (Object object : resultList) {
            Object[] result = (Object[]) object;
            Article article = new Article();
            article.setId(Long.parseLong(result[0].toString()));
            article.setArticle((String) result[1]);
            article.setDescription((String) result[2]);
            article.setDate((Date) (result[3]));
            User user = new User();
            user.setId(Long.parseLong(result[4].toString()));
            user.setName((String) result[5]);
            user.setLastname((String) result[6]);
            Comment comment = new Comment();
            User userForComment = new User();
            userForComment.setName((String) result[7]);
            userForComment.setLastname((String) result[8]);
            userForComment.setId(Long.parseLong(result[9].toString()));
            comment.setDate((Date) (result[10]));
            comment.setContent((String) result[11]);
            article.setUser(user);
            comment.setUser(userForComment);
            article.setComment(Collections.singletonList(comment));
            articles.add(article);
        }
        return articles;
    }
}
