package com.gmail.iikaliada.onlinemarket.repositorymodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.ReviewRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.exception.IllegalDatabaseStateException;
import com.gmail.iikaliada.onlinemarket.repositorymodule.exception.IllegalFormatStatementRepositoryException;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Review;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.gmail.iikaliada.onlinemarket.repositorymodule.constant.LimitConstants.LIMIT;
import static com.gmail.iikaliada.onlinemarket.repositorymodule.constant.RepositoryConstants.DATABASE_STATE_MESSAGE;
import static com.gmail.iikaliada.onlinemarket.repositorymodule.constant.RepositoryConstants.STATEMENT_REPOSITORY_MESSAGE;

@Repository
public class ReviewRepositoryImpl extends GenericRepositoryImpl<Long, Review> implements ReviewRepository {

    private final static Logger logger = LoggerFactory.getLogger(ReviewRepositoryImpl.class);

    @Override
    public List<Review> getReview(Connection connection, int pageSize) {
        List<Review> reviews = new ArrayList<>();
        String userQuery = "SELECT c.*, u.name, u.lastname FROM reviews c JOIN user u ON c.user_id = u.id LIMIT ? OFFSET ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(userQuery)) {
            preparedStatement.setInt(1, LIMIT);
            preparedStatement.setInt(2, getOffsetForReview(pageSize));
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

    private int getOffsetForReview(int pageSize) {
        return (pageSize - 1) * LIMIT;
    }

    private Review getReviewFromDB(ResultSet resultSet) {
        try {
            Review review = new Review();
            review.setId(resultSet.getLong("id"));
            review.setText(resultSet.getString("review"));
            review.setDate(resultSet.getTimestamp("date"));
            review.setShown(resultSet.getBoolean("shown"));
            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setName(resultSet.getString("name"));
            user.setLastname(resultSet.getString("lastname"));
            review.setUser(user);
            return review;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new IllegalDatabaseStateException(DATABASE_STATE_MESSAGE);
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
    public void changeStatus(Connection connection, List<Review> reviews) {
        String statusQuery = "UPDATE reviews SET shown = ? where id = ? AND shown <> ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(statusQuery)) {
            updateReviews(reviews, preparedStatement);
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

    private void updateReviews(List<Review> reviews, PreparedStatement preparedStatement) throws SQLException {
        for (Review review : reviews) {
            preparedStatement.setBoolean(1, review.isShown());
            preparedStatement.setLong(2, review.getId());
            preparedStatement.setBoolean(3, review.isShown());
            preparedStatement.addBatch();
        }
    }
}
