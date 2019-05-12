package com.gmail.iikaliada.onlinemarket.servicemodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.ReviewRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Review;
import com.gmail.iikaliada.onlinemarket.servicemodule.ReviewService;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.ReviewConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.exception.ConnectionServiceStateException;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ReviewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final static Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);
    private static final String CONNECTION_SERVICE_MESSAGE = "Cannot create connection";

    private ReviewRepository reviewRepository;
    private ReviewConverter reviewConverter;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewConverter reviewConverter) {
        this.reviewRepository = reviewRepository;
        this.reviewConverter = reviewConverter;
    }

    @Override
    public List<ReviewDTO> getReviews(int pageSize) {
        try (Connection connection = reviewRepository.getConnection()) {
            List<ReviewDTO> reviewDTOS;
            connection.setAutoCommit(false);
            try {
                List<Review> reviews = reviewRepository.getReview(connection, pageSize);
                reviewDTOS = reviews.stream().map(reviewConverter::toReviewDTO).collect(Collectors.toList());
                connection.commit();
                return reviewDTOS;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
        }
    }

    @Override
    public int getTotalPagesForReview() {
        try (Connection connection = reviewRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int totalPages = reviewRepository.getTotalPagesForReview(connection);
                connection.commit();
                return totalPages;
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
        }
    }

    @Override
    public void changeStatus(Long ids) {
        try (Connection connection = reviewRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                reviewRepository.changeStatus(connection, ids);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
        }
    }

    @Override
    public void changeStatus1(Long collect) {
        try (Connection connection = reviewRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                reviewRepository.changeStatus1(connection, collect);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
        }
    }

    @Override
    public void deleteReview(Long id) {
        try (Connection connection = reviewRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                reviewRepository.deleteReview(connection, id);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
        }
    }
}
