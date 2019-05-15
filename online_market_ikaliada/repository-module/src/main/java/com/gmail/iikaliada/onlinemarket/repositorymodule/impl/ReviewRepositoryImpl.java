package com.gmail.iikaliada.onlinemarket.repositorymodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.ReviewRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.exception.IllegalDatabaseStateException;
import com.gmail.iikaliada.onlinemarket.repositorymodule.exception.IllegalFormatStatementRepositoryException;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Review;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.UserForReview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewRepositoryImpl extends ConnectionRepositoryImpl implements ReviewRepository {

    private static final Logger logger = LoggerFactory.getLogger(ReviewRepositoryImpl.class);
    private static final String DATABASE_STATE_MESSAGE = "Database exception during getting user";
    private static final String STATEMENT_REPOSITORY_MESSAGE = "Cannot execute SQL query ";
    private static final int LIMIT = 10;

    @Override
    public List<Review> getReview(Connection connection, int pageSize) {
        List<Review> reviews = new ArrayList<>();
        String userQuery = "SELECT c.*, u.name, u.lastname FROM reviews c JOIN user u ON c.user_id = u.id LIMIT ? OFFSET ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(userQuery)) {
            preparedStatement.setInt(1, LIMIT);
            preparedStatement.setInt(2, getOffset(pageSize));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Review review = getReviewFromDB(resultSet);
                reviews.add(review);
            }
            return reviews;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalFormatStatementRepositoryException(STATEMENT_REPOSITORY_MESSAGE + userQuery);
        }
    }

    @Override
    public int getTotalPagesForReview(Connection connection) {
        String countQuery = "SELECT COUNT(*) FROM reviews";
        int pagesNumber = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(countQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int amountRows = resultSet.getInt(1);
                pagesNumber = amountRows / LIMIT;
                if (amountRows % LIMIT > 0) {
                    pagesNumber += 1;
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalFormatStatementRepositoryException(STATEMENT_REPOSITORY_MESSAGE + countQuery);
        }
        return pagesNumber;
    }

    @Override
    public void changeStatus(Connection connection, Long ids) {
        String statusQuery = "UPDATE reviews SET shown = IF(shown = 1, 0, 0) where id in (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(statusQuery)) {
            preparedStatement.setLong(1, ids);
            preparedStatement.addBatch();
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalFormatStatementRepositoryException(STATEMENT_REPOSITORY_MESSAGE + statusQuery);
        }
    }

    @Override
    public void deleteReview(Connection connection, Long id) {
        String deleteQuery = "DELETE FROM reviews WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalFormatStatementRepositoryException(STATEMENT_REPOSITORY_MESSAGE + deleteQuery);
        }
    }

    private Review getReviewFromDB(ResultSet resultSet) {
        try {
            Review comment = new Review();
            comment.setId(resultSet.getLong("id"));
            comment.setText(resultSet.getString("review"));
            comment.setDate(resultSet.getTimestamp("date"));
            comment.setShown(resultSet.getBoolean("shown"));
            UserForReview userForComment = new UserForReview();
            userForComment.setId(resultSet.getLong("user_id"));
            userForComment.setName(resultSet.getString("name"));
            userForComment.setLastname(resultSet.getString("lastname"));
            comment.setUserForReview(userForComment);
            return comment;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new IllegalDatabaseStateException(DATABASE_STATE_MESSAGE);
        }
    }

    private int getOffset(int pageSize) {
        return (pageSize - 1) * LIMIT;
    }
}
