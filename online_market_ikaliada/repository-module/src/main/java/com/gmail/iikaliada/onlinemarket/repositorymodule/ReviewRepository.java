package com.gmail.iikaliada.onlinemarket.repositorymodule;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Review;

import java.sql.Connection;
import java.util.List;

public interface ReviewRepository extends GenericRepository<Long, Review> {

    List<Review> getReview(Connection connection, int pageSize);

    int getTotalPagesForReview(Connection connection);

    void changeStatus(Connection connection, List<Review> reviews);

    void deleteReview(Connection connection, Long id);
}
