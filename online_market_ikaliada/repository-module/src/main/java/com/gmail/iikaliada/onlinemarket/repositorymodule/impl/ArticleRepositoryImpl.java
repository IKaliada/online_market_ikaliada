package com.gmail.iikaliada.onlinemarket.repositorymodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.ArticleRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.exception.IllegalDatabaseStateException;
import com.gmail.iikaliada.onlinemarket.repositorymodule.exception.IllegalFormatStatementRepositoryException;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Article;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Comment;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.gmail.iikaliada.onlinemarket.repositorymodule.constant.RepositoryConstants.DATABASE_STATE_MESSAGE;
import static com.gmail.iikaliada.onlinemarket.repositorymodule.constant.RepositoryConstants.STATEMENT_REPOSITORY_MESSAGE;

@Repository
public class ArticleRepositoryImpl extends GenericRepositoryImpl<Long, Article> implements ArticleRepository {

    private final static Logger logger = LoggerFactory.getLogger(ArticleRepositoryImpl.class);

    @Override
    public Article getArticleByKeyWord(Connection connection, String keyWord) {
        String keyWordQuery = "SELECT a.*, u.name, u.lastname, us.name, us.lastname, " +
                "c.id as comment_id, c.date as comment_date, c.content " +
                "FROM articles a JOIN user u ON a.user_id = u.id JOIN comment c ON a.id = c.article_id " +
                "JOIN user us ON c.user_id = us.id WHERE MATCH (description) AGAINST (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(keyWordQuery)) {
            preparedStatement.setString(1, keyWord);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getArticle(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalFormatStatementRepositoryException(STATEMENT_REPOSITORY_MESSAGE + keyWordQuery);
        }
        return null;
    }

    private Article getArticle(ResultSet resultSet) {
        try {
            Article article = new Article();
            article.setId(resultSet.getLong("id"));
            article.setDescription(resultSet.getString("description"));
            article.setArticle(resultSet.getString("article"));
            article.setDate(resultSet.getTimestamp("date"));
            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setName(resultSet.getString("name"));
            user.setLastname(resultSet.getString("lastname"));
            Comment comment = new Comment();
            comment.setId(resultSet.getLong("comment_id"));
            comment.setDate(resultSet.getTimestamp("comment_date"));
            comment.setContent(resultSet.getString("content"));
            comment.setUser(user);
            article.setUser(user);
            List<Comment> comments = new ArrayList<>(Collections.singletonList(comment));
            article.setComment(comments);
            return article;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new IllegalDatabaseStateException(DATABASE_STATE_MESSAGE);
        }
    }
}